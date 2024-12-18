package com.example.jetpackcompose.presentation.ui.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.data.dataModel.DayOfWeek
import com.example.jetpackcompose.domain.usecase.GetPatchHistoryUseCase
import com.example.jetpackcompose.domain.usecase.GetPlanUseCase
import com.example.jetpackcompose.presentation.ui.UIState.PlanUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class PlanViewModel @Inject constructor(
    private val getPatchHistoryUseCase: GetPatchHistoryUseCase,
    private val getPlanUseCase: GetPlanUseCase
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

                val latestWeek = patch?.weeks?.lastOrNull()
                val currentYearMonth = latestWeek?.startDate?.substring(0, 7) // "YYYY-MM"

                val currentYear = currentYearMonth?.substring(0, 4)?.toInt() ?: LocalDate.now().year
                val currentMonth = currentYearMonth?.substring(5, 7)?.toInt() ?: LocalDate.now().monthValue

                // Generate a 6x7 grid (42 days)
                val firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1)
                val firstDayOffset = firstDayOfMonth.dayOfWeek.value % 7 // Adjust for Monday = 0
                val totalDaysInMonth = YearMonth.of(currentYear, currentMonth).lengthOfMonth()
                val today = LocalDate.now()

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
                                else -> 5 // Today (not missed)
                            }
                        }
                    }
                }

                val mappedDays = daysList.mapIndexed { index, date ->
                    date.format(DateTimeFormatter.ISO_DATE) to dayType[index]
                }

                val missedCnt = patch?.weeks?.sumOf { it.missedSessions } ?: 0
                val totalHour = patch?.weeks?.sumOf { it.totalTime }?.toFloat() ?: 0f
                val totalSession = patch?.weeks?.sumOf { it.sessionCount } ?: 0


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

    fun dateToDayOfWeek(date: LocalDate): DayOfWeek {
        return when (date.dayOfWeek.value) {
            1 -> DayOfWeek.MONDAY
            2 -> DayOfWeek.TUESDAY
            3 -> DayOfWeek.WEDNESDAY
            4 -> DayOfWeek.THURSDAY
            5 -> DayOfWeek.FRIDAY
            6 -> DayOfWeek.SATURDAY
            7 -> DayOfWeek.SUNDAY
            else -> DayOfWeek.SUNDAY
        }
    }





    fun UpdateValue(newValue: String){

    }
}