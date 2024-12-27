package com.example.jetpackcompose.domain.repo

import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.Plan
import com.example.jetpackcompose.data.dataModel.WeekSummary
import com.example.jetpackcompose.data.dataModel.Workout
import java.time.LocalDate


interface WorkoutRepository {
    suspend fun getYourWorkouts(): List<Workout>
    suspend fun getWorkoutById(workoutId: String): Workout?

    suspend fun getExerciseFromWorkout(workoutId: String): List<Exercise>

    suspend fun updateWorkout(workoutId: String, updatedWorkout: Workout): Boolean
    suspend fun createWorkout(workout: Workout): Boolean
    suspend fun deleteWorkout(workoutId: String): Boolean

    suspend fun getWorkoutStreak(): Int
    suspend fun resetWorkoutStreak()
    suspend fun addWorkoutStreak()

    suspend fun getPatchHistory(cnt:Int=4, skip:Int=0): PatchHistory?
    suspend fun addSessionToHistory(duraMilis:Float): Boolean
    suspend fun addMissedDaysToHistory(missedDates: List<LocalDate>)

    suspend fun getPlan(): Plan
    suspend fun updatePlan(plan: Plan): Boolean
}
