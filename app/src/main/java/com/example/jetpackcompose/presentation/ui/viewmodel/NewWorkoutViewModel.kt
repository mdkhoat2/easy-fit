package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.data.dataModel.generateWorkoutID
import com.example.jetpackcompose.data.dataModel.getAllExercises
import com.example.jetpackcompose.data.dataModel.getExerciseIcon
import com.example.jetpackcompose.domain.usecase.CreateWorkoutUseCase
import com.example.jetpackcompose.domain.usecase.GetCustomExerciseUseCase
import com.example.jetpackcompose.presentation.ui.uiState.WorkoutEditUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewWorkoutViewModel @Inject constructor(
    private val createWorkoutUseCase: CreateWorkoutUseCase,
    private val getCustomExerciseUseCase: GetCustomExerciseUseCase
) : BaseWorkoutViewModel() {

    init {
        _state.value = WorkoutEditUIState()
        viewModelScope.launch {
            refreshCustomExercises()
        }
    }

    suspend fun refreshCustomExercises() {
        try {
            val customExercises = getCustomExerciseUseCase()
            val exercises = getAllExercises() + customExercises
            val availableExercises = exercises.map { it to getExerciseIcon(it) }

            _state.value = _state.value.copy(
                availableExercises = availableExercises
            )
        } catch (e: Exception) {
            Log.e("NewWorkoutViewModel", "Error refreshing exercises", e)
        }
    }

    suspend fun onSavePressed() {
        // generate workout

        val workout = Workout(
            id = generateWorkoutID(),
            creatorId = null,
            name = state.value.workoutName,
            exercises = state.value.queueExercise.map { it.first },
            duration = state.value.queueExercise.sumOf { it.first.duration }
        )

        // save workout
        try {
            createWorkoutUseCase.invoke(workout)
            _state.value = _state.value.copy(
                workoutName = "",
                queueExercise = _state.value.queueExercise,
            )
        } catch (e: Exception) {
            Log.e("NewWorkoutViewModel", "Error saving workout", e)
        }
    }
}
