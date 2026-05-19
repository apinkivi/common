package fi.apinkivi.common.net

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Domain(
    val value: String,
) {
    val packageName get() = value.split('.').reversed().joinToString(".")
    val root get() = value.substringBeforeLast('.').substringAfterLast('.')
}
