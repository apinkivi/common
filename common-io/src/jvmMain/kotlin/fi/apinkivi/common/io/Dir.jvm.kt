package fi.apinkivi.common.io

import java.io.File as JvmFile

val Dir.jvm get() = JvmFile(path)

val JvmFile.dir get() = Dir(path.replace(WIN_PATH, PATH))

actual fun Dir.files(extension: String) = jvm.listFiles { it.extension == extension && it.isFile }?.map { it.file } ?: emptyList()
