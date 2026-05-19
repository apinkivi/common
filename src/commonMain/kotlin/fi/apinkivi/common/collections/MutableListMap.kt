package fi.apinkivi.common.collections

@Deprecated("Use [kotlin.collections.LinkedHashMap] instead")
abstract class MutableListMap<K, V>(
    override val list: MutableList<V>,
) : ListMap<K, V>(list),
    MutableMap<K, V> {
    data class Entry<K, V>(
        override val key: K,
        override var value: V,
    ) : MutableMap.MutableEntry<K, V> {
        override fun setValue(newValue: V): V {
            val oldValue = value
            value = newValue
            return oldValue
        }
    }

    override val keys get() = list.map { it.key }.toMutableSet()
    override val values get() = list
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = list.map { Entry(it.key, it) }.toMutableSet()

    fun indexByKey(key: K) = list.indexOfFirst { it.key == key }

    override fun put(
        key: K,
        value: V,
    ) = indexByKey(key).let {
        if (it == -1) {
            list.add(value)
            null
        } else {
            val oldValue = list[it]
            list[it] = value
            oldValue
        }
    }

    override fun remove(key: K) = indexByKey(key).let { if (it == -1) null else list.removeAt(it) }

    override fun putAll(from: Map<out K, V>) {
        from.forEach { (key, value) -> put(key, value) }
    }

    override fun clear() = list.clear()
}
