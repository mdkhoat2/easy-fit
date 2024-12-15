package com.example.jetpackcompose.presentation.ui.UIState

import com.example.jetpackcompose.data.dataModel.Workout

data class SelectWorkoutUIState(
    val workouts: List<Workout> = emptyList(),
    val filteredWorkouts: List<Workout> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true
)
