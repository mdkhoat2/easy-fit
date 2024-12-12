package com.example.jetpackcompose.domain.repo

import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.Workout


interface WorkoutRepository {

    //suspend fun getPatchHistory(cnt:Int, skip:Int): PatchHistory
    suspend fun getYourWorkouts(): List<Workout>
<<<<<<< Updated upstream
=======
    suspend fun getWorkoutById(workoutId: String): Workout?
>>>>>>> Stashed changes

    suspend fun updateWorkout(workoutId: String, updatedWorkout: Workout): Boolean
    suspend fun createWorkout(workout: Workout): Boolean
    suspend fun deleteWorkout(workoutId: String): Boolean
}
