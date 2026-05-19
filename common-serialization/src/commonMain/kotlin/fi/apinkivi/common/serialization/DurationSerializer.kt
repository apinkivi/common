package fi.apinkivi.common.serialization

import kotlin.time.Duration

abstract class DurationSerializer(
    suffix: String,
) : LongSerializer<Duration>("Duration$suffix") {
    abstract override fun deserialize(value: Long): Duration
}
