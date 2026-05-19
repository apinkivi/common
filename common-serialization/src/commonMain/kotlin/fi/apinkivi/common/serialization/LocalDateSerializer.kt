package fi.apinkivi.common.serialization

import fi.apinkivi.common.datetime.localDate
import kotlinx.datetime.LocalDate

object LocalDateSerializer : StringSerializer<LocalDate>(LocalDate::class) {
    override fun deserialize(value: String) = localDate(value)
}
