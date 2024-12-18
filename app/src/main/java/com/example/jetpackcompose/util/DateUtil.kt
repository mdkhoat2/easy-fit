package com.example.jetpackcompose.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

fun getStartOfCurrentWeek(): LocalDate {
    return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
}

fun getCurrentDate(): LocalDate {
    return LocalDate.now()
}

fun getDateString(date: LocalDate): String {
    return date.toString()
}