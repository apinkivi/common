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

    val i4 get() = parseInt(4)

    fun i4(move: Int = 1) = parseInt(4, move)
}

fun localDate(text: String) =
    Parser(text).run {
        when (length) {
            14 -> LocalDate(i4, i2, i2)

            // 20001231
            19 -> LocalDate(i4(), i2(), i2)

            // 2000-12-31
            else -> LocalDate.parse(trimmed)
        }
    }

fun localDateTime(text: String) =
    Parser(text).run {
        when (length) {
            14 -> LocalDateTime(i4, i2, i2, i2, i2, i2)

            // 20001231123456
            15 -> LocalDateTime(i4, i2, i2(), i2, i2, i2)

            // 20001231T123456
            19 -> LocalDateTime(i4(), i2(), i2(), i2(), i2(), i2)

            // 2000-12-31T12:34:56
            else -> LocalDateTime.parse(trimmed)
        }
    }

fun localTime(text: String) =
    Parser(text).run {
        when (length) {
            6 -> LocalTime(i2, i2, i2)

            // 123456
            8 -> LocalTime(i2(), i2(), i2)

            // 12:34:56
            else -> LocalTime.parse(trimmed)
        }
    }
