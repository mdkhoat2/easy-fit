package com.example.jetpackcompose.domain.usecase

import com.example.jetpackcompose.data.dataModel.WeeklyHistory
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.domain.repo.WorkoutRepository

class GetWeeklyHistoryUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(userId: String,cnt:Int,skip:Int): WeeklyHistory {

        return repository.getWeeklyHistory(userId,cnt,skip)
    }
}



