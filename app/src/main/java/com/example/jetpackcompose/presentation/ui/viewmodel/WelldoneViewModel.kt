package com.example.jetpackcompose.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import com.example.jetpackcompose.presentation.ui.uiState.SelectWorkoutUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WelldoneViewModel @Inject constructor(private val getYourWorkoutsUseCase: GetYourWorkoutsUseCase) : ViewModel() {
    private val _state = MutableStateFlow(SelectWorkoutUIState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val workouts = getYourWorkoutsUseCase.invoke() // Pass the user ID as needed
                _state.value = _state.value.copy(
                    workouts = workouts,
                    isLoading = false
                )
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

        // Optionally filter workouts based on the search query
        val filteredWorkouts = _state.value.workouts.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _state.value = _state.value.copy(workouts = filteredWorkouts)
    }
}
