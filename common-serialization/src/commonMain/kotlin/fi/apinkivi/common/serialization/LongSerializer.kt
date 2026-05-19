package fi.apinkivi.common.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KClass

abstract class LongSerializer<T : Any>(
    serialName: String,
) : KSerializer<T> {
    final override val descriptor = PrimitiveSerialDescriptor(serialName, PrimitiveKind.LONG)

    constructor(type: KClass<T>) : this(type.simpleName!!)

    final override fun deserialize(decoder: Decoder) = deserialize(decoder.decodeLong())

    abstract fun deserialize(value: Long): T

    final override fun serialize(
        encoder: Encoder,
        value: T,
    ) = encoder.encodeLong(serialize(value))

    open fun serialize(value: T): Long = throw NotImplementedError()
}
