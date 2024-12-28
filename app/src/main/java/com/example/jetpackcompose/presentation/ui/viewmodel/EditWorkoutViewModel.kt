package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.data.dataModel.generateWorkoutID
import com.example.jetpackcompose.data.dataModel.getAllExercises
import com.example.jetpackcompose.data.dataModel.getExerciseIcon
import com.example.jetpackcompose.domain.usecase.CreateWorkoutUseCase
import com.example.jetpackcompose.domain.usecase.EditWorkoutUseCase
import com.example.jetpackcompose.domain.usecase.GetWorkoutByIdUseCase
import com.example.jetpackcompose.presentation.ui.uiState.WorkoutEditUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class EditWorkoutViewModel @Inject constructor(
    private val editWorkoutUseCase: EditWorkoutUseCase,
    private val getWorkoutByIdUseCase: GetWorkoutByIdUseCase
) : ViewModel(), WorkoutViewModel {
    private val _state = MutableStateFlow(WorkoutEditUIState())
    override val state = _state.asStateFlow()

    private var workoutId: String = ""

    // Load workout data by ID
    suspend fun loadWorkout(workoutId: String) {
        try {
            val workout = getWorkoutByIdUseCase(workoutId) ?: throw IllegalArgumentException("Workout not found")
            this.workoutId = workoutId

            val exercises = getAllExercises()
            val availableExercises = exercises.map { it to getExerciseIcon(it) }

            _state.value = WorkoutEditUIState(
                workoutName = workout.name,
                queueExercise = workout.exercises.map { it to getExerciseIcon(it) },
                availableExercises = availableExercises
            )
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

        // Clear the state after saving
        _state.value = WorkoutEditUIState()
    }

    fun onExerciseSelected(exercise: String) {
        val newExercise = state.value.availableExercises.find { it.first.name.toString() == exercise } ?: return
        val newQueue = state.value.queueExercise.toMutableList()
        newQueue.add(newExercise)
        _state.value = state.value.copy(queueExercise = newQueue)
    }

    fun onWorkoutNameChanged(name: String) {
        if (name.length > 20) return
        _state.value = state.value.copy(workoutName = name)
    }

    override fun updateExercise(exerciseIndex: Int, updatedExercise: Exercise) {
        val newQueue = _state.value.queueExercise.toMutableList()
        newQueue[exerciseIndex] = Pair(updatedExercise, newQueue[exerciseIndex].second)
        _state.value = _state.value.copy(queueExercise = newQueue)
    }

    override fun onExerciseRemoved(index: Int) {
        val newQueue = _state.value.queueExercise.toMutableList()
        if (index < newQueue.size) {
            newQueue.removeAt(index)
            _state.value = _state.value.copy(queueExercise = newQueue)
        }
    }
}
