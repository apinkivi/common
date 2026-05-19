package fi.apinkivi.common.serialization

import fi.apinkivi.common.datetime.localDateTime
import kotlinx.datetime.LocalDateTime

object LocalDateTimeSerializer : StringSerializer<LocalDateTime>(LocalDateTime::class) {
    override fun deserialize(value: String) = localDateTime(value)
}
