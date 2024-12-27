package com.example.jetpackcompose.presentation.ui.uiState

import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.Plan

data class PlanUIState(
    val patchHistory: PatchHistory? = null,
    val missedCnt: Int = 0,
    val totalHour: Float = 0f,
    val totalSession: Int = 0,
    val plan: Plan? = null,
    val dayType: List<Int> = List(35) { 0 },
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)