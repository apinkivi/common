package fi.apinkivi.common.collections

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface TailIterator<out T> :
    Iterator<T>,
    AutoCloseable {
    /**
     * Block until a new element becomes available.
     *
     * Polls the sequence at the given interval. When a new element arrives, it is returned.
     *
     * @param pollInterval How often to check for new elements (default 1 s).
     * @return The next element, never null (blocks indefinitely until an element arrives).
     */
    fun awaitNext(pollInterval: Duration = 1.seconds): T
}
