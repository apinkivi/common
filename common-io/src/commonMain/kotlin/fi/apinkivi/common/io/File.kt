package fi.apinkivi.common.io

import fi.apinkivi.common.io.Entry.Companion.safe
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class File(
    override val path: String,
) : Entry {
    val baseName get() = name.substringBefore(EXT)

    val extension get() = name.substringAfterLast(EXT)

    val extensions get() = name.substringAfter(EXT)

    val safe get() = File(path.safe)

    fun validate() {
        require(!path.contains(WIN_PATH)) { "Use '$PATH' instead if '$WIN_PATH' in file path: $path" }
        require(!path.endsWith(PATH)) { "File path '$path' cannot end to '$PATH'" }
    }

    override fun toString() = path
}

expect val File.exists: Boolean
expect var File.text: String
