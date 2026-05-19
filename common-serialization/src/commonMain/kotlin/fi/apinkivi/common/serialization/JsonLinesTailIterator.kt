package fi.apinkivi.common.serialization

import fi.apinkivi.common.collections.TailIterator
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlin.time.Duration

class JsonLinesTailIterator<T>(
    iterator: TailIterator<String>,
    deserializer: DeserializationStrategy<T>,
    json: Json = Json,
) : JsonLinesIterator<T>(iterator, deserializer, json),
    TailIterator<T> {
    override val iterator = super.iterator as TailIterator<String>

    override fun awaitNext(pollInterval: Duration) = decode(iterator.awaitNext(pollInterval))

    override fun close() = iterator.close()
}
