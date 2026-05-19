package fi.apinkivi.common.serialization

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object DurationSecondsSerializer : DurationSerializer("Seconds") {
    override fun deserialize(value: Long) = value.seconds

    override fun serialize(value: Duration) = value.inWholeSeconds
}
