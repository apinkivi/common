package fi.apinkivi.common.serialization

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json

open class JsonLinesIterator<T>(
    open val iterator: Iterator<String>,
    val deserializer: DeserializationStrategy<T>,
    val json: Json = Json,
) : Iterator<T> {
    fun decode(value: String) = json.decodeFromString(deserializer, value)

    override fun next() = decode(iterator.next())

    override fun hasNext() = iterator.hasNext()
}
