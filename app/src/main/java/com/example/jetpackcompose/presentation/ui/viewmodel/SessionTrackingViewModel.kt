package com.example.jetpackcompose.presentation.ui.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.R
import com.example.jetpackcompose.domain.usecase.GetExerciseFromWorkoutUseCase
import com.example.jetpackcompose.presentation.di.ExerciseItem
import com.example.jetpackcompose.presentation.di.ExerciseUIType
import com.example.jetpackcompose.presentation.di.toExerciseItem
import com.example.jetpackcompose.presentation.ui.uiState.SessionTrackingUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionTrackingViewModel @Inject constructor(
    private val getExerciseFromWorkoutUseCase: GetExerciseFromWorkoutUseCase,
    private val workoutId: String,
    context: Context
): ViewModel(){
    private val _state = MutableStateFlow(SessionTrackingUIState())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null
    private var lastTimestamp = 0L

    // MediaPlayer instance
    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer().apply {
            // Initialize with an audio file (e.g., from raw folder or external source)
            val descriptor = context.resources.openRawResourceFd(R.raw.complete)
            setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            descriptor.close()
            prepare()
        }
    }

    init {
        // Load data asynchronously
        viewModelScope.launch {
            Log.d("SessionTrackingViewModel", "workoutId: $workoutId")
            val exercises = fetchData()
            _state.value = _state.value.copy(
                exercises = exercises,
                currentExerciseIndex = 0,
                startTime = System.currentTimeMillis())
            startTimer()
        }
    }

    private suspend fun  fetchData(): List<ExerciseItem> {
        // Fetch data from the database
        val exercises = getExerciseFromWorkoutUseCase.invoke(workoutId)

        val exerciseItems = exercises.map { it.toExerciseItem() }
        // how to get rid of the rest that have duration 0


        return exerciseItems
            .flatMap { listOf(it.first, it.second) }
            .filter { it.name != "Delete"}
    }

    fun nextExercise(){
        val currentExerciseIndex = _state.value.currentExerciseIndex
        if (currentExerciseIndex < _state.value.exercises.size - 1){
            _state.value = _state.value.copy(currentExerciseIndex = currentExerciseIndex + 1)
        }
        else{
            endTimer()
        }
        playMedia()
    }

    fun isEnd(): Boolean{
        return _state.value.currentExerciseIndex == _state.value.exercises.size - 1
    }

    fun togglePause(){
        _state.value = _state.value.copy(isPaused = !_state.value.isPaused)
        if (_state.value.isPaused){
            lastTimestamp = 0
        }
    }

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

    private fun endTimer(){
        timerJob?.cancel()
    }

    fun setExerciseIndex(index: Int){
        _state.value = _state.value.copy(currentExerciseIndex = index)
    }

    // Play media using MediaPlayer
    private fun playMedia() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(0) // Restart the audio if it's already playing
        } else {
            mediaPlayer.start()
            Log.d("MediaPlayer", "MediaPlayer started")
        }
    }

    // Release MediaPlayer when the ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}