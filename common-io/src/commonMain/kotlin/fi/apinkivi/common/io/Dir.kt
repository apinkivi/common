package fi.apinkivi.common.io

import fi.apinkivi.common.io.Entry.Companion.safe
import kotlinx.serialization.Serializable

/** Directory */
@JvmInline
@Serializable
value class Dir(
    override val path: String,
) : Entry {
    val safe get() = Dir(path.safe)

    constructor(path: String, vararg paths: String) : this("$path/${paths.joinToString("/")}".safe)

    fun file(name: String) = File("$path/${name.safe}")

    fun validate() {
        require(!path.contains(WIN_PATH)) { "Use '$PATH' instead if '$WIN_PATH' in the directory path: $path" }
        require(!path.endsWith(PATH)) { "Directory path '$path' cannot end with '$PATH'" }
    }

    override fun toString() = path
}

expect fun Dir.files(extension: String): List<File>
