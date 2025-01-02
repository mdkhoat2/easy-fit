package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.data.dataModel.getAllExercises
import com.example.jetpackcompose.data.dataModel.getExerciseIcon
import com.example.jetpackcompose.domain.usecase.EditWorkoutUseCase
import com.example.jetpackcompose.domain.usecase.GetCustomExerciseUseCase
import com.example.jetpackcompose.domain.usecase.GetWorkoutByIdUseCase
import com.example.jetpackcompose.presentation.ui.uiState.WorkoutEditUIState
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditWorkoutViewModel @Inject constructor(
    private val editWorkoutUseCase: EditWorkoutUseCase,
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase,
    private val getCustomExerciseUseCase: GetCustomExerciseUseCase,
) : BaseWorkoutViewModel() {

    private var workoutId: String = ""
    private var hasLoaded = false // Tracks whether the workout data has been loaded

    init {
        // If getCustomExerciseUseCase returns a Flow
        viewModelScope.launch {
            try {
                refreshCustomExercises()
            } catch (e: Exception) {
                Log.e("EditWorkoutViewModel", "Error loading custom exercises", e)
            }
        }
    }


    suspend fun refreshCustomExercises() {
        val customExercises = getCustomExerciseUseCase() // Get the latest custom exercises
        val allExercises = getAllExercises() + customExercises
        val availableExercises = allExercises.map { it to getExerciseIcon(it) }

        _state.value = _state.value.copy(
            availableExercises = availableExercises
        )
    }

    suspend fun loadWorkout(workoutId: String) {
        if (this.workoutId != workoutId) hasLoaded = false // Reset loaded state if workout ID changes
        if (hasLoaded) return // Skip loading if already loaded

        try {
            val workout = getWorkoutByIdUseCase(workoutId) ?: throw IllegalArgumentException("Workout not found")
            this.workoutId = workoutId

            val customExercises = getCustomExerciseUseCase()
            val exercises = getAllExercises() + customExercises

            val availableExercises = exercises.map { it to getExerciseIcon(it) }

            _state.value = WorkoutEditUIState(
                workoutName = workout.name,
                queueExercise = workout.exercises.map { it to getExerciseIcon(it) },
                availableExercises = availableExercises
            )


            hasLoaded = true // Mark as loaded
        } catch (e: Exception) {
            Log.e("EditWorkoutViewModel", "Error loading workout", e)
        }
    }

    suspend fun onSavePressed() {
        val currentState = _state.value

        val updatedWorkout = Workout(
            id = workoutId,
            creatorId = null,
            name = currentState.workoutName,
            exercises = currentState.queueExercise.map { it.first },
            duration = currentState.queueExercise.sumOf { it.first.duration },
        )

        editWorkoutUseCase(workoutId,updatedWorkout)
        workoutId = ""
        // Clear the state after saving
        _state.value = WorkoutEditUIState()
    }
}
