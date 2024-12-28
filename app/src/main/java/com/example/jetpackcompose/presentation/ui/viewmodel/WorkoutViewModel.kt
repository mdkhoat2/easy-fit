package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.presentation.ui.uiState.WorkoutEditUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface WorkoutViewModel {
    val state: StateFlow<WorkoutEditUIState>

    fun updateExercise(exerciseIndex: Int, updatedExercise: Exercise)
    fun onExerciseRemoved(index: Int)
    fun onExerciseSelected(exercise: String)
    fun onWorkoutNameChanged(name: String)
}

abstract class BaseWorkoutViewModel : ViewModel(), WorkoutViewModel {
    protected val _state = MutableStateFlow(WorkoutEditUIState())
    override val state: StateFlow<WorkoutEditUIState> = _state

    override fun onExerciseSelected(exercise: String) {
        val newExercise = _state.value.availableExercises.find { it.first.name.toString() == exercise } ?: return
        val newQueue = _state.value.queueExercise.toMutableList()
        newQueue.add(newExercise)
        _state.value = _state.value.copy(queueExercise = newQueue)
    }

    override fun onWorkoutNameChanged(name: String) {
        if (name.length > 20) return
        _state.value = _state.value.copy(workoutName = name)
    }

    override fun updateExercise(exerciseIndex: Int, updatedExercise: Exercise) {
        val newQueue = _state.value.queueExercise.toMutableList()
        if (exerciseIndex in newQueue.indices) {
            newQueue[exerciseIndex] = Pair(updatedExercise, newQueue[exerciseIndex].second)
            _state.value = _state.value.copy(queueExercise = newQueue)
        }
        Log.d("BaseWorkoutViewModel", "updateExercise: $updatedExercise")
    }

    override fun onExerciseRemoved(index: Int) {
        val newQueue = _state.value.queueExercise.toMutableList()
        if (index in newQueue.indices) {
            newQueue.removeAt(index)
            _state.value = _state.value.copy(queueExercise = newQueue)
        }
    }
}


