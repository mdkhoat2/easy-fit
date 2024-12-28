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
) : BaseWorkoutViewModel() {

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
}
