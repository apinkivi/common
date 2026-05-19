package fi.apinkivi.common.io

import java.io.File as JvmFile

actual val File.exists get() = jvm.exists()

val File.jvm get() = JvmFile(path)

actual var File.text
    get() = jvm.readText()
    set(value) =
        jvm.run {
            parentFile.mkdirs()
            writeText(value)
        }

val JvmFile.file get() = File(path.replace(WIN_PATH, PATH))
