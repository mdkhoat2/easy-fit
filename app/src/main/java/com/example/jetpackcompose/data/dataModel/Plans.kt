package com.example.jetpackcompose.data.dataModel

import java.time.LocalDate

data class Plan(
    val name: String="",
    val dateWorkout: List<DayOfWeek>,
    val timeWorkout: List<String>,
    val maxMissDay: Int=0,
    val minSession: Int=0,
    val minHour: Float=0f,
)

data class PatchHistory(
    val weeks: List<WeekSummary> // Each week contains aggregated data
)

data class WeekSummary(
    val startDate: String,
    val missedSessions: Int,
    val totalTime: Float, // In minutes
    val sessionCount: Int,
    val missedDays: List<DayOfWeek>
)

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

fun dateToDayOfWeek(date: LocalDate): DayOfWeek {
    return when (date.dayOfWeek.value) {
        1 -> DayOfWeek.MONDAY
        2 -> DayOfWeek.TUESDAY
        3 -> DayOfWeek.WEDNESDAY
        4 -> DayOfWeek.THURSDAY
        5 -> DayOfWeek.FRIDAY
        6 -> DayOfWeek.SATURDAY
        7 -> DayOfWeek.SUNDAY
        else -> DayOfWeek.SUNDAY
    }
}