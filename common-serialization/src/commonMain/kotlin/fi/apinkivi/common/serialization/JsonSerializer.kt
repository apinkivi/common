package fi.apinkivi.common.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class JsonSerializer<T : Any>(
    private val serializer: KSerializer<T>,
) : StringSerializer<T>(serializer.descriptor.serialName + "Json") {
    override fun deserialize(value: String): T {
        try {
            return Json.decodeFromString(serializer, value)
        } catch (e: Exception) {
            cantDecode(value, e)
        }
    }

    override fun serialize(value: T) = Json.encodeToString(serializer, value)
}
