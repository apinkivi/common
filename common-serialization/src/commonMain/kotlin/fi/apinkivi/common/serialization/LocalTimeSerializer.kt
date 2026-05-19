package fi.apinkivi.common.serialization

import fi.apinkivi.common.datetime.localTime
import kotlinx.datetime.LocalTime

object LocalTimeSerializer : StringSerializer<LocalTime>(LocalTime::class) {
    override fun deserialize(value: String) = localTime(value)
}
