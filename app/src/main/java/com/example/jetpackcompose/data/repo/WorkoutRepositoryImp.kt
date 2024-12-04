package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.dataModel.WeeklyHistory
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.domain.repo.WorkoutRepository


// Data Layer
class WorkoutRepositoryImp(
    private val api: WorkoutApi,
    private val database: WorkoutDatabase
) : WorkoutRepository {
    override suspend fun getWeeklyHistory(userId: String,cnt: Int,skip: Int): WeeklyHistory {


        return WeeklyHistory()
    }

    override suspend fun getMonthlyHistory(userId: String): List<WeeklyHistory> {
        return listOf(WeeklyHistory())
    }

    override suspend fun getYourWorkouts(userId: String): List<Workout> {
        return listOf(Workout())
    }

    override suspend fun updateWorkout(workoutId: String, updatedWorkout: Workout): Boolean {
        return true
    }

    override suspend fun createWorkout(workout: Workout): Boolean {
        return true
    }
}

