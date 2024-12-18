package com.example.jetpackcompose.data.dataModel

data class Plan(
    val name: String="",
    val dateWorkout: List<DayOfWeek>,
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
    val totalTime: Int, // In minutes
    val sessionCount: Int,
    val missedDays: List<DayOfWeek>
)

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}