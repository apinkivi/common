package fi.apinkivi.common.io

import fi.apinkivi.common.collections.TailIterator
import java.io.RandomAccessFile
import kotlin.time.Duration

/**
 * JVM implementation using [RandomAccessFile] in read-only mode.
 *
 * `RandomAccessFile("r")` does not acquire an exclusive lock on Windows or Linux,
 * so other processes can freely read/write the same file concurrently.
 *
 * Maintains a byte-position cursor and an internal buffer for partial-line handling.
 */
actual class FileLinesReader actual constructor(
    file: File,
) : TailIterator<String> {
    private val file = RandomAccessFile(file.path, "r")

    /** Leftover bytes from the last read that didn't end with a newline. */
    private var remainder = ""

    /** Reusable read buffer. */
    private val buffer = ByteArray(BUFFER_SIZE)

    /** Cached next line for [hasNext]/[next] contract. */
    private var nextLine: String? = null

    actual override fun hasNext(): Boolean {
        if (nextLine != null) return true
        nextLine = readLine()
        return nextLine != null
    }

    actual override fun next(): String {
        val line =
            if (nextLine != null) {
                nextLine!!
            } else {
                readLine() ?: throw NoSuchElementException("No more lines available")
            }
        nextLine = null
        return line
    }

    actual override fun awaitNext(pollInterval: Duration): String {
        while (true) {
            val line = readLine()
            if (line != null) {
                // Clear cached state so hasNext() re-checks file
                nextLine = null
                return line
            }

            @Suppress("BusyWait")
            Thread.sleep(pollInterval.inWholeMilliseconds)
        }
    }

    actual override fun close() {
        file.close()
    }

    /**
     * Read the next complete line from the file.
     *
     * Returns the line content (without the trailing newline), or `null`
     * if no complete line is available right now.
     */
    private fun readLine(): String? {
        while (true) {
            val newlineIdx = remainder.indexOf('\n')
            if (newlineIdx >= 0) {
                val line = remainder.substring(0, newlineIdx).trimEnd('\r')
                remainder = remainder.substring(newlineIdx + 1)
                return line
            }

            val bytesRead = file.read(buffer)
            if (bytesRead <= 0) return null

            remainder += String(buffer, 0, bytesRead, Charsets.UTF_8)
        }
    }

    companion object {
        /** Buffer size (8 KB) */
        const val BUFFER_SIZE = 8192
    }
}
