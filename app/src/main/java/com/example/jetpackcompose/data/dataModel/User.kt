package com.example.jetpackcompose.data.dataModel

data class User(
    val id: String,
    val email: String,
    val username: String,
    val password: String,
    val userDetail: UserDetail,
)

data class UserDetail(
    val id: String,
    val description: String,
    val interests: String,
    val isVerified: Boolean,
    val avatarUrl: String,
    val goals: Goals
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
    val startDate: String,
    val missedSessions: Int,
    val totalTime: Int, // In minutes
    val sessionCount: Int
)
