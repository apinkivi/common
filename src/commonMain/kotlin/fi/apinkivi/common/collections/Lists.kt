package fi.apinkivi.common.collections

/** Usually you should use [LinkedHashSet] instead. */
fun <T> MutableList<T>.addIfAbsent(element: T) = if (element !in this) add(element) else false
