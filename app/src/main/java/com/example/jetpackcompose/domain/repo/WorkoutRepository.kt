package com.example.jetpackcompose.domain.repo

import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.Workout


interface WorkoutRepository {

    suspend fun getPatchHistory(userId: String, cnt:Int, skip:Int): PatchHistory
    suspend fun getYourWorkouts(): List<Workout>
    suspend fun getWorkoutById(workoutId: String): Workout

    suspend fun updateWorkout(workoutId: String, updatedWorkout: Workout): Boolean
    suspend fun createWorkout(workout: Workout): Boolean
    suspend fun deleteWorkout(workoutId: String): Boolean

    suspend fun getWorkoutStreak(): Int
    suspend fun resetWorkoutStreak(): Boolean
    suspend fun addWorkoutStreak(): Boolean
}