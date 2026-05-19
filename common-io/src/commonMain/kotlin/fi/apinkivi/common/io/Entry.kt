package fi.apinkivi.common.io

import fi.apinkivi.common.removeSuffixes
import fi.apinkivi.common.replace

/** File system entry (file or directory). */
interface Entry {
    /** File or directory absolute path. */
    val path: String

    /** Full name of the file or the directory. */
    val name get() = path.substringAfterLast(PATH)

    /** Parent directory, or null if it's on the root. */
    val parent get() = path.substringBeforeLast(PATH, "").run { if (isEmpty()) null else Dir(this) }

    /** True if this is a root file or directory. */
    val root get() = parent == null

    companion object {
        /**
         * 1. Replaces Windows path separator with the standard path separator.
         * 2. Replaces `< > : " | ? *` with `( ) ! ' ! ! x`.
         * 3. Removes `/. ` suffixes.
         */
        val String.safe get() =
            replace(WIN_PATH, PATH)
                .replace(
                    '<' to '(',
                    '>' to ')',
                    ':' to '!',
                    '"' to '\'',
                    '|' to '!',
                    '?' to '!',
                    '*' to 'x',
                ).removeSuffixes(PATH, '.', ' ')
    }
}
