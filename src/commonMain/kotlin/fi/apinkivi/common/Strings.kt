package fi.apinkivi.common

private val CAMEL_CASE = Regex("(\\p{Ll})(\\p{Lu})")

val String.camelCaseHyphenated get() = CAMEL_CASE.replace(this, "$1\u00AD$2")

private fun String.chain(
    action: String.(Char) -> String,
    chars: CharArray,
): String {
    var result = this
    for (char in chars) {
        result = result.action(char)
    }
    return result
}

fun String.removeSuffix(suffix: Char) = removeSuffix(suffix.toString())

fun String.removeSuffixes(vararg suffixes: Char): String {
    var result = this
    do {
        val before = result
        result = chain(String::removeSuffix, suffixes)
    } while (result != before)
    return result
}

fun String.replace(vararg pairs: Pair<Char, Char>): String {
    var result = this
    pairs.forEach { (from, to) -> result = result.replace(from, to) }
    return result
}

fun String.strBeforeChained(vararg delimiters: Char) = chain(String::substringBefore, delimiters)

fun String.strAfterChained(vararg delimiters: Char) = chain(String::substringAfter, delimiters)

fun String.strBetweenFirst(
    start: Char,
    end: Char,
) = substringAfter(start).substringBefore(end)

fun String.strBetweenFirst(
    start: String,
    end: String,
) = substringAfter(start).substringBefore(end)
