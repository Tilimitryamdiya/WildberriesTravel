package com.example.wildberriestravel.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateTimeFormatter {

    private fun parseDate(date: String): LocalDateTime {
        val reformat = "${date.take(10)}T${date.substring(11..18)}"
        return LocalDateTime.parse(reformat)
    }

    fun formatDate(date: String): String {
        val parsedDate = parseDate(date)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return formatter.format(parsedDate)
    }

    fun formatTime(date: String): String {
        val parsedDate = parseDate(date)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return formatter.format(parsedDate)
    }

    fun countTimeFlight(startDate: String, endDate: String): String {
        val departure = parseDate(startDate)
        val arrival = parseDate(endDate)
        return ChronoUnit.HOURS.between(departure, arrival).toString()
    }

}