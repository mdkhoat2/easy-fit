package com.example.jetpackcompose.presentation.ui.viewmodel

import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.presentation.ui.uiState.WorkoutEditUIState
import kotlinx.coroutines.flow.StateFlow

interface WorkoutViewModel {
    val state: StateFlow<WorkoutEditUIState>

    fun updateExercise(exerciseIndex: Int, updatedExercise: Exercise)
    fun onExerciseRemoved(index: Int)


}


