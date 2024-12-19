package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import com.example.jetpackcompose.presentation.ui.uiState.SelectWorkoutUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectWorkoutViewModel @Inject constructor(private val getYourWorkoutsUseCase: GetYourWorkoutsUseCase) : ViewModel() {
    private val _state = MutableStateFlow(SelectWorkoutUIState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val workouts = getYourWorkoutsUseCase.invoke()
                _state.value = _state.value.copy(
                    workouts = workouts,
                    isLoading = false
                )
                Log.d("SelectWorkoutViewModel", "Workouts: $workouts")
            } catch (e: Exception) {
                // Handle errors (e.g., show an error message)
                _state.value = _state.value.copy(
                    isLoading = false,
                )
            }
        }
        _state.value = _state.value.copy(isLoading = true)
    }

    fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(searchQuery = query)

        val filteredWorkouts = _state.value.workouts.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _state.value = _state.value.copy(searchQuery = query, filteredWorkouts = filteredWorkouts)
    }

    fun getWorkoutById(workoutId: String): Workout {
        return state.value.workouts.find { it.id == workoutId }
            ?: throw IllegalArgumentException("Workout not found")
    }

}
