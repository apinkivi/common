package fi.apinkivi.common.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class JsonSerializer<T : Any>(
    private val serializer: KSerializer<T>,
) : StringSerializer<T>(serializer.descriptor.serialName + "Json") {
    override fun deserialize(value: String): T {
        try {
            return Json.decodeFromString(serializer, value)
        } catch (e: SerializationException) {
            cantDecode(value, e)
        } catch (e: IllegalArgumentException) {
            cantDecode(value, e)
        }
    }

    override fun serialize(value: T) = Json.encodeToString(serializer, value)
}
