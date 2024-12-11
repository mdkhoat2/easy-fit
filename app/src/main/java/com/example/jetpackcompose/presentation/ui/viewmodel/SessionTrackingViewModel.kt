package com.example.jetpackcompose.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.presentation.di.ExerciseItem
import com.example.jetpackcompose.presentation.ui.UIState.SessionTrackingUIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SessionTrackingViewModel : ViewModel(){
    private val _state = MutableStateFlow(SessionTrackingUIState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null
    private var lastTimestamp = 0L

    init {
        // Load data asynchronously
        viewModelScope.launch {
            val exercises = fetchData()
            _state.value = _state.value.copy(
                exercises = exercises,
                currentExerciseIndex = 0,
                startTime = System.currentTimeMillis())
            startTimer()
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

    fun togglePause(){
        _state.value = _state.value.copy(isPaused = !_state.value.isPaused)
        if (_state.value.isPaused){
            lastTimestamp = 0
        }
    }

//    fun startWorkout(){
//        viewModelScope.launch {
//            _state.value = _state.value.copy(
//                currentExerciseIndex = 0,
//
//            )
//        }
//        startTimer()
//    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                if(!_state.value.isPaused){
                    val currentTime = System.currentTimeMillis()
                    if (lastTimestamp != 0L){
                        val delta = currentTime - lastTimestamp
                        _state.value = _state.value.copy(elapsedTime = _state.value.elapsedTime + delta)
                    }
                    lastTimestamp = currentTime
                }
                delay(1000)
            }
        }
    }
}