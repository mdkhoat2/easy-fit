package com.example.jetpackcompose.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.presentation.di.ExerciseItem
import com.example.jetpackcompose.presentation.ui.UIState.SessionTrackingUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionTrackingViewModel : ViewModel(){
    private val _state = MutableStateFlow(SessionTrackingUIState())
    val state = _state.asStateFlow()

    init {
        // Load data asynchronously
        viewModelScope.launch {
            val exercises = fetchData()
            _state.value = _state.value.copy(exercises = exercises, currentExerciseIndex = 0)
        }
    }

    private suspend fun fetchData(): List<ExerciseItem> {
        // Simulate data fetching
        return listOf(
            ExerciseItem.SitUp,
            ExerciseItem.PushUp,
            ExerciseItem.JumpingRope
        )
    }

    fun nextExercise(){
        val currentExerciseIndex = _state.value.currentExerciseIndex
        if (currentExerciseIndex < _state.value.exercises.size - 1){
            _state.value = _state.value.copy(currentExerciseIndex = currentExerciseIndex + 1)
        }
    }
}