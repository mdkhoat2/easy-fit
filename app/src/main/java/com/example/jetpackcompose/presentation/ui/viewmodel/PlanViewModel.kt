package com.example.jetpackcompose.presentation.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.dataModel.DayOfWeek
import com.example.jetpackcompose.data.dataModel.dateToDayOfWeek
import com.example.jetpackcompose.domain.usecase.GetPatchHistoryUseCase
import com.example.jetpackcompose.domain.usecase.GetPlanUseCase
import com.example.jetpackcompose.presentation.ui.uiState.PlanUIState
import com.example.jetpackcompose.util.getLastDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class PlanViewModel @Inject constructor(
    private val getPatchHistoryUseCase: GetPatchHistoryUseCase,
    private val getPlanUseCase: GetPlanUseCase,
    private val lastDate: String
    ) : ViewModel() {
    private val _state = MutableStateFlow(PlanUIState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val patch = getPatchHistoryUseCase.invoke()
                val plan = getPlanUseCase.invoke()

                val today = LocalDate.now()

                val currentYear = today.year
                val currentMonth = today.monthValue

                // Generate a 6x7 grid (42 days)
                val firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1)
                val firstDayOffset = firstDayOfMonth.dayOfWeek.value % 7 // Adjust for Monday = 0
                val totalDaysInMonth = YearMonth.of(currentYear, currentMonth).lengthOfMonth()



                val dayType = MutableList(42) { 0 } // Default: 0 = Out of month
                val daysList = mutableListOf<LocalDate>()

                // Leading blanks (previous month's days)
                for (i in firstDayOffset downTo 1) {
                    daysList.add(firstDayOfMonth.minusDays(i.toLong()))
                }
                // Current month's days
                for (day in 1..totalDaysInMonth) {
                    daysList.add(firstDayOfMonth.withDayOfMonth(day))
                }
                // Trailing blanks (next month's days)
                while (daysList.size < 42) {
                    daysList.add(daysList.last().plusDays(1))
                }

                // Map day statuses
                patch?.weeks?.forEach { week ->
                    week.missedDays.forEach { dayOfWeek ->
                        val date = LocalDate.parse(week.startDate).plusDays(dayOfWeek.ordinal.toLong())
                        val dayIndex = daysList.indexOf(date)
                        if (dayIndex in dayType.indices && daysList[dayIndex].monthValue == currentMonth) {
                            dayType[dayIndex] = 3 // Missed day
                        }
                    }
                }

                daysList.forEachIndexed { index, date ->
                    if (daysList[index].monthValue == currentMonth) {
                        if (dayType[index] == 0) { // Only set if not already missed
                            dayType[index] = when {
                                date.isAfter(today) ->
                                    if (plan.dateWorkout.contains(dateToDayOfWeek(date))) 4 else 1
                                date.isBefore(today) -> 2 // Past, not missed
                                else -> // Today 5 mean completed, 6 mean not completed
                                    if (lastDate==LocalDate.now().toString()) 5 else
                                        if (plan.dateWorkout.contains(dateToDayOfWeek(date))) 7 else
                                            6
                            }
                        }
                    }
                }

                val filteredWeeks = patch?.weeks?.filter { week ->
                    val weekStartDate = LocalDate.parse(week.startDate)
                    val weekEndDate = weekStartDate.plusDays(6)
                    weekStartDate.monthValue == currentMonth || weekEndDate.monthValue == currentMonth
                } ?: emptyList()

                var missedCnt = 0
                var totalHour = 0f
                var totalSession = 0

                filteredWeeks.forEach { week ->
                    val weekStartDate = LocalDate.parse(week.startDate)

                    // Iterate through each day in the week
                    for (dayOffset in 0..6) {
                        val currentDate = weekStartDate.plusDays(dayOffset.toLong())
                        if (currentDate.year == currentYear && currentDate.monthValue == currentMonth) {
                            val customDayOfWeek = dateToDayOfWeek(currentDate)

                            if (week.missedDays.contains(customDayOfWeek)) {
                                missedCnt += 1
                            }
                        }
                    }
                    totalHour += (week.totalTime / 60)
                    totalSession += week.sessionCount
                }

                _state.value = _state.value.copy(
                    missedCnt = missedCnt,
                    totalHour = totalHour,
                    totalSession = totalSession,
                    plan = plan,
                    patchHistory = patch,
                    dayType = dayType,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "An error occurred"
                )
            }
        }
    }
}

