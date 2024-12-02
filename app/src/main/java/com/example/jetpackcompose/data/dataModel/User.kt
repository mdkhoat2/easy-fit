package com.example.jetpackcompose.data.dataModel

data class User(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
    val streak: Int = 0, // Number of consecutive workout days
    val goals: Goals? // User-specific goals
)

data class Goals(
    val calories: Int,
    val days: Int,
    val hours: Int // Total workout hours
)

data class WeeklyHistory(
    val weeks: List<WeekSummary> // Each week contains aggregated data
)

data class WeekSummary(
    val weekNumber: Int,
    val missedSessions: Int,
    val totalTime: Int, // In minutes
    val sessionCount: Int
)
