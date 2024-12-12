package com.example.jetpackcompose.data.database

import android.content.Context
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.data.persistentStorage.PersistentStorageManager

class WorkoutDatabase private constructor(private val context: Context) {
    // Local database implementation

    companion object {
        @Volatile
        private var instance: WorkoutDatabase? = null

        fun getInstance(context: Context): WorkoutDatabase {
            return instance ?: synchronized(this) {
                PersistentStorageManager.loadWorkoutsFromFile(context)
                PersistentStorageManager.loadStreakFromFile(context)
                PersistentStorageManager.fillWithMockData()
                instance ?: WorkoutDatabase(context.applicationContext).also { instance = it }
            }
        }
    }

    // Database here
    fun getAllWorkouts() = PersistentStorageManager.getWorkouts()

    fun addWorkout(workout: Workout) {
        val workouts = PersistentStorageManager.getWorkouts().toMutableList()
        workouts.add(workout)
        PersistentStorageManager.saveWorkoutsToFile(context)
    }

    // add WorkoutID to the ediWorkout function
    fun editWorkout(id:String,workout: Workout): Boolean {
        val workouts = PersistentStorageManager.getWorkouts().toMutableList()
        val index = workouts.indexOfFirst { it.id == id }
        if (index == -1) {
            return false
        }
        workouts[index] = workout
        PersistentStorageManager.saveWorkoutsToFile(context)
        return true
    }

    fun getExerciseFromWorkout(workoutId: String): List<Exercise> {
        val workout = PersistentStorageManager.getWorkouts().find { it.id == workoutId }
        return workout?.exercises ?: emptyList()
    }

    fun getWorkoutStreak() = PersistentStorageManager.getStreak()

    fun updateWorkoutStreak(newStreak: Int) {
        PersistentStorageManager.setStreak(newStreak)
        PersistentStorageManager.saveStreakToFile(context)
    }
    fun getWorkoutById(workoutId: String): Workout? {
        return PersistentStorageManager.getWorkouts().find { it.id == workoutId }
    }
}

