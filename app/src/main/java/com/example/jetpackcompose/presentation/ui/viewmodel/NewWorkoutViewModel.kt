package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.data.dataModel.generateWorkoutID
import com.example.jetpackcompose.data.dataModel.getAllExercises
import com.example.jetpackcompose.data.dataModel.getExerciseIcon
import com.example.jetpackcompose.domain.usecase.CreateWorkoutUseCase
import com.example.jetpackcompose.presentation.ui.uiState.WorkoutEditUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NewWorkoutViewModel @Inject constructor(
    private val createWorkoutUseCase: CreateWorkoutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WorkoutEditUIState())
    val state = _state.asStateFlow()
    val newOrEdit: Boolean = true

    init {
        _state.value = WorkoutEditUIState()
        loadData()
    }

    private fun loadData() {
        val exercises = getAllExercises()

        val availableExercises = exercises.map { it to getExerciseIcon(it) }

        _state.value = state.value.copy(
            queueExercise = emptyList(),
            availableExercises = availableExercises,
            workoutName = ""
        )
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

    fun onExerciseSelected(exercise: String) {
        val newExercise = state.value.availableExercises.find { it.first.name.toString() == exercise } ?: return
        val newQueue = state.value.queueExercise.toMutableList()
        newQueue.add(newExercise)
        _state.value = state.value.copy(queueExercise = newQueue)
    }

    fun onExerciseRemoved(index: Int) {
        val newQueue = state.value.queueExercise.toMutableList()
        if (index < newQueue.size) {
            newQueue.removeAt(index)
            _state.value = state.value.copy(queueExercise = newQueue)
        }
    }

    fun onWorkoutNameChanged(name: String) {
        if (name.length > 20) return
        _state.value = state.value.copy(workoutName = name)
    }

    fun updateExercise(exerciseIndex : Int, updatedExercise : Exercise){
        val newQueue = state.value.queueExercise.toMutableList()
        newQueue[exerciseIndex] = Pair(updatedExercise,newQueue[exerciseIndex].second)
        _state.value = state.value.copy(queueExercise = newQueue)
    }
}
