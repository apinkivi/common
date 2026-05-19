package fi.apinkivi.common

const val BASE94 = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"

val Int.base94 get(): String {
    var value = toUInt()
    if (value == 0u) return BASE94[0].toString()
    val result = StringBuilder()
    while (value > 0u) {
        val index = (value % 94u).toInt()
        result.append(BASE94[index])
        value /= 94u
    }
    return result.toString()
}

fun String.intAfterChained(vararg delimiters: Char) = strAfterChained(*delimiters).toInt()

fun String.intBefore(delimiter: Char) = substringBefore(delimiter).toInt()

fun String.intBetweenFirst(
    start: Char,
    end: Char,
) = strBetweenFirst(start, end).toInt()
