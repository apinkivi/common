package fi.apinkivi.common.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.enums.enumEntries

open class OrdinalSerializer<E : Enum<E>>(
    val entries: List<E>,
) : KSerializer<E> {
    override val descriptor =
        PrimitiveSerialDescriptor("${entries.first()::class.simpleName}Ordinal", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder) = entries[decoder.decodeInt()]

    override fun serialize(
        encoder: Encoder,
        value: E,
    ) = encoder.encodeInt(value.ordinal)
}

inline fun <reified E : Enum<E>> ordinalSerializer() = OrdinalSerializer<E>(enumEntries())
