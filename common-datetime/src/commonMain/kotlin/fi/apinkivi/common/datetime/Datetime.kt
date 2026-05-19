package fi.apinkivi.common.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

private class Parser(
    text: String,
) {
    val trimmed = text.trim()
    val length = trimmed.length
    private var index = 0

    private fun parseInt(
        chars: Int,
        move: Int = 0,
    ) = trimmed.substring(index, index + chars).toInt().also {
        index += chars + move
    }

    val i2 get() = parseInt(2)

    fun i2(move: Int = 1) = parseInt(2, move)

    val i4 get() = parseInt(YEAR)

    fun i4(move: Int = 1) = parseInt(YEAR, move)

    companion object {
        const val YEAR = 4

        /** 20001231 */
        const val ISO_DATE_BASIC = 8

        /** 2000-12-31 */
        const val ISO_DATE_EXTENDED = 10

        /** 123456 */
        const val ISO_TIME_BASIC = 6

        /** 12:34:56 */
        const val ISO_TIME_EXTENDED = 8

        /** 20001231123456 */
        const val DATE_TIME_COMPACT = 14

        /** 20001231T123456 */
        const val ISO_DATE_TIME_BASIC = 15

        /** 2000-12-31T12:34:56 */
        const val ISO_DATE_TIME_EXTENDED = 19
    }
}

fun localDate(text: String) =
    Parser(text).run {
        when (length) {
            Parser.ISO_DATE_BASIC -> LocalDate(i4, i2, i2)
            Parser.ISO_DATE_EXTENDED -> LocalDate(i4(), i2(), i2)
            else -> LocalDate.parse(trimmed)
        }
    }

fun localTime(text: String) =
    Parser(text).run {
        when (length) {
            Parser.ISO_TIME_BASIC -> LocalTime(i2, i2, i2)
            Parser.ISO_TIME_EXTENDED -> LocalTime(i2(), i2(), i2)
            else -> LocalTime.parse(trimmed)
        }
    }

fun localDateTime(text: String) =
    Parser(text).run {
        when (length) {
            Parser.DATE_TIME_COMPACT -> LocalDateTime(i4, i2, i2, i2, i2, i2)
            Parser.ISO_DATE_TIME_BASIC -> LocalDateTime(i4, i2, i2(), i2, i2, i2)
            Parser.ISO_DATE_TIME_EXTENDED -> LocalDateTime(i4(), i2(), i2(), i2(), i2(), i2)
            else -> LocalDateTime.parse(trimmed)
        }
    }
