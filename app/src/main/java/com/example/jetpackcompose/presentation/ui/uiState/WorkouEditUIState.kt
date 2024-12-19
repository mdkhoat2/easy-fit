package com.example.jetpackcompose.presentation.ui.uiState

import com.example.jetpackcompose.data.dataModel.Exercise

data class WorkoutEditUIState(
    val queueExercise: List<Pair<Exercise,Int>> = emptyList(),
    val availableExercises: List<Pair<Exercise,Int>> = emptyList(),
    var workoutName: String = ""

)