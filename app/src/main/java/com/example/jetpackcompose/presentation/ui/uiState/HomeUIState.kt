package com.example.jetpackcompose.presentation.ui.uiState

import co.yml.charts.common.model.Point
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.presentation.di.StatData

data class HomeUIState(
    val patchHistory: PatchHistory? = null,
    val points: List<Point> = emptyList(),
    val listData: List<StatData> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)