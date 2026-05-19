package fi.apinkivi.common

val Any.hash get() = System.identityHashCode(this).base94
