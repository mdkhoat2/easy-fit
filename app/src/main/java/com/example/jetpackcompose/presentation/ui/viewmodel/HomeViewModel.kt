package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.jetpackcompose.domain.usecase.GetPatchHistoryUseCase
import com.example.jetpackcompose.presentation.di.StatData
import com.example.jetpackcompose.presentation.ui.UIState.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel @Inject constructor(private val getPatchHistoryUseCase: GetPatchHistoryUseCase) : ViewModel() {
    private val _state = MutableStateFlow(HomeUIState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                // Call the suspend function inside the coroutine
                val patch = getPatchHistoryUseCase.invoke()

                // each point is a week in the patch history y is the number of sessions count is the week number
                val points = patch?.weeks?.mapIndexed { index, weekSummary ->
                    Point(index.toFloat(), weekSummary.sessionCount.toFloat())
                } ?: emptyList()

                // List of data to be displayed in the list
                // get the data from the patch history

                //get total missed sessions
                //get total time spent
                //get total sessions

                val listData = patch?.weeks?.let {
                    val missed = it.sumOf { it.missedSessions }
                    val time = it.sumOf { it.totalTime }
                    val session = it.sumOf { it.sessionCount }
                    listOf(
                        StatData("Missed", missed.toString()),
                        StatData("Time", "${time / 60}:${time % 60}"),
                        StatData("Session", session.toString())
                    )
                } ?: emptyList()

                _state.value = _state.value.copy(
                    points = points,
                    isLoading = false,
                    patchHistory = patch,
                    listData = listData
                )
                Log.d("HomeViewModel", "Patch history loaded: $patch")
                Log.d("HomeViewModel", "Points loaded: $points")
            } catch (e: Exception) {
                // Handle errors appropriately
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun UpdateValue(newValue: String){
        _state.value.listData[0].value = newValue
    }
}