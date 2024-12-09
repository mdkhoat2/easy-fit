package com.example.jetpackcompose.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import com.example.jetpackcompose.presentation.di.StatData
import com.example.jetpackcompose.presentation.ui.UIState.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeUIState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData(){
        _state.value = _state.value.copy(isLoading = true)

        val points = listOf(
            Point(0f, 40f),
            Point(1f, 90f),
            Point(2f, 0f),
            Point(3f, 60f),
            Point(4f, 10f)
        )

        val listData = listOf(
            StatData("Missed", "10"),
            StatData("Time", "10:04"),
            StatData("Session", "2")
        )

        _state.value = _state.value.copy(
            points = points,
            listData = listData,
            isLoading = false
        )

    }

    fun UpdateValue(newValue: String){
        _state.value.listData[0].value = newValue
    }
}