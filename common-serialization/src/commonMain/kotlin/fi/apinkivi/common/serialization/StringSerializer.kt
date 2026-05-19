package fi.apinkivi.common.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KClass

abstract class StringSerializer<T : Any>(
    serialName: String,
) : KSerializer<T> {
    constructor(type: KClass<T>) : this("${type.simpleName}")

    final override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)

    final override fun deserialize(decoder: Decoder) = deserialize(decoder.decodeString())

    abstract fun deserialize(value: String): T

    final override fun serialize(
        encoder: Encoder,
        value: T,
    ) = encoder.encodeString(serialize(value))

    open fun serialize(value: T) = value.toString()

    companion object {
        fun cantDecode(
            value: String,
            cause: Throwable? = null,
        ): Nothing = throw SerializationException("Can't decode string: $value", cause)

        fun cantDecode(
            value: String,
            what: String,
            cause: Throwable? = null,
        ): Nothing = throw SerializationException("Can't decode $what from string: $value", cause)
    }
}
