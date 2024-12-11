package com.example.jetpackcompose.presentation.ui.UIState

import com.example.jetpackcompose.presentation.di.ExerciseItem

data class SessionTrackingUIState(
    val exercises: List<ExerciseItem> = emptyList(),
    val currentExerciseIndex: Int = 0
){
    val currentExercise: ExerciseItem?
        get() = exercises.getOrNull(currentExerciseIndex)
}