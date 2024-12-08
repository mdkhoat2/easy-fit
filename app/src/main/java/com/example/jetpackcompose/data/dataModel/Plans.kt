package com.example.jetpackcompose.data.dataModel

data class Plan(
    val id: String,
    val name: String,
    val workouts: List<PlannedWorkout>, // Workouts scheduled for each day of the week
    val description: String? = null // Optional description of the plan
)

data class PlannedWorkout(
    val dayOfWeek: DayOfWeek, // The day of the week the workout is assigned (e.g., Monday)
    val workout: Workout
)

data class PatchHistory(
    val weeks: List<WeekSummary> // Each week contains aggregated data
)

data class WeekSummary(
    val planId: String,
    val startDate: String,
    val missedSessions: Int,
    val totalTime: Int, // In minutes
    val sessionCount: Int,
    val missedDays: List<DayOfWeek>
)

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

data class PlanTracker(
    val plan: Plan,
    val currentDay: Int, // Current day of the plan
    val completed: List<Status>
)

enum class Status {
    COMPLETED,
    MISSED,
    UPCOMING
}