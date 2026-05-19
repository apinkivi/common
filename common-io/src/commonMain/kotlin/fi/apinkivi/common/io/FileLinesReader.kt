package fi.apinkivi.common.io

import fi.apinkivi.common.collections.TailIterator
import kotlin.time.Duration

/**
 * Streaming, non-blocking file reader that reads a text file line by line
 * without preventing other processes from reading or writing the same file.
 *
 * Designed for `tail -f` style consumption of growing files (e.g., JSONL logs).
 *
 * Implements [Iterator] so it can be used in `for` loops and with
 * stdlib extension functions like `forEach`, `take`, `map`, etc.
 *
 * Platform implementations use shared file access modes:
 * - **JVM**: `RandomAccessFile("r")` which does not acquire an exclusive lock
 * - **Windows native** _(future)_: `CreateFileW` with `FILE_SHARE_READ | FILE_SHARE_WRITE`
 *
 * Usage:
 * ```kotlin
 * val reader = FileLinesReader("logs/session.jsonl")
 *
 * // Iterator — non-blocking, reads available lines
 * for (line in reader) {
 *     println(line)
 * }
 *
 * // Blocking — polls until a new line appears
 * val newLine = reader.awaitNext(pollIntervalMs = 200)
 *
 * reader.close()
 * ```
 */
expect class FileLinesReader(
    file: File,
) : TailIterator<String> {
    /**
     * Returns `true` if a complete line is available to read **right now**.
     *
     * This method does not block — it returns `false` immediately at EOF
     * even if the file may grow later.
     */
    override fun hasNext(): Boolean

    /**
     * Returns the next complete line (without a trailing newline).
     *
     * @throws NoSuchElementException if no complete line is available.
     */
    override fun next(): String

    /**
     * Block until a new complete line becomes available.
     *
     * Polls the file at the given interval. When new data arrives and
     * contains a complete line (terminated by `\n`), the line is returned.
     *
     * @param pollInterval How often to check for new data (default 100 ms).
     * @return The next line, never null (blocks indefinitely until data arrives).
     */
    override fun awaitNext(pollInterval: Duration): String

    /**
     * Close the underlying file handle and release resources.
     */
    override fun close()
}
