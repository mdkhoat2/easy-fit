package com.example.jetpackcompose.presentation.ui.uiState

import com.example.jetpackcompose.presentation.di.ExerciseItem

data class SessionTrackingUIState(
    val exercises: List<ExerciseItem> = emptyList(),
    val currentExerciseIndex: Int = 0,
    val isPaused: Boolean = false,
    val elapsedTime: Long = 0,
    val startTime: Long = 0
){
    val currentExercise: ExerciseItem?
        get() = exercises.getOrNull(currentExerciseIndex)
}