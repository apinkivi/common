package fi.apinkivi.common.collections

abstract class ListMap<K, out V>(
    protected open val list: List<V>,
) : Map<K, V> {
    data class Entry<out K, out V>(
        override val key: K,
        override val value: V,
    ) : Map.Entry<K, V>

    override val size get() = list.size
    override val keys get() = list.map { it.key }.toSet()
    override val values get() = list
    override val entries: Set<Map.Entry<K, V>> get() = list.map { Entry(it.key, it) }.toSet()

    abstract val @UnsafeVariance V.key: K

    init {
        // require(list.distinctBy { getKey(it) }.size == list.size) { "List contains duplicate keys" }
    }

    override fun isEmpty() = list.isEmpty()

    override fun containsKey(key: K) = list.any { it.key == key }

    override fun containsValue(value: @UnsafeVariance V) = list.contains(value)

    override fun get(key: K) = list.firstOrNull { it.key == key }
}
