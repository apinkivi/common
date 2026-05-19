package fi.apinkivi.common.util

import java.util.Properties

fun properties(path: String) =
    Properties()
        .apply {
            load(
                Thread
                    .currentThread()
                    .contextClassLoader
                    .getResourceAsStream(("${path.replace(".", "/")}.properties")),
            )
        }.map { it.key.toString() to it.value.toString() }
        .toMap()
