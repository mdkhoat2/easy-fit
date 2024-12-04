package com.example.jetpackcompose.domain.repo

import com.example.jetpackcompose.data.dataModel.WeeklyHistory
import com.example.jetpackcompose.data.dataModel.Workout


interface WorkoutRepository {

    suspend fun getWeeklyHistory(userId: String,cnt:Int,skip:Int): WeeklyHistory
    suspend fun getYourWorkouts(userId: String): List<Workout>

    suspend fun updateWorkout(workoutId: String, updatedWorkout: Workout): Boolean
    suspend fun createWorkout(workout: Workout): Boolean
}
