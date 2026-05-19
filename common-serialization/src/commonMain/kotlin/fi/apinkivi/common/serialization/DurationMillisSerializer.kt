package fi.apinkivi.common.serialization

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object DurationMillisSerializer : DurationSerializer("Millis") {
    override fun deserialize(value: Long) = value.milliseconds

    override fun serialize(value: Duration) = value.inWholeMilliseconds
}
