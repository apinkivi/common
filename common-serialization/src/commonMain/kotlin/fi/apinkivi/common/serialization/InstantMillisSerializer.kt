package fi.apinkivi.common.serialization

import kotlin.time.Instant

object InstantMillisSerializer : LongSerializer<Instant>("InstantMillis") {
    override fun deserialize(value: Long) = Instant.fromEpochMilliseconds(value)

    override fun serialize(value: Instant) = value.toEpochMilliseconds()
}
