package com.example.jetpackcompose.data.database

import android.content.Context
import com.example.jetpackcompose.data.dataModel.DayOfWeek
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.ExerciseName
import com.example.jetpackcompose.data.dataModel.ExerciseType
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.WeekSummary
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

                instance ?: WorkoutDatabase(context).also { instance = it }

            }
        }
    }

    // Database here
    fun getAllWorkouts(): List<Workout> {
        return PersistentStorageManager.loadWorkoutsFromFile(context)
    }

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

    fun getWorkoutStreak(): Int {
        return PersistentStorageManager.getStreak()
    }

    fun updateWorkoutStreak(newStreak: Int) {
        PersistentStorageManager.setStreak(newStreak)
        PersistentStorageManager.saveStreakToFile(context)
    }

    fun resetWorkoutStreak() {
        PersistentStorageManager.setStreak(0)
        PersistentStorageManager.saveStreakToFile(context)
    }

    fun getWorkoutById(workoutId: String): Workout? {
        return PersistentStorageManager.getWorkouts().find { it.id == workoutId }
    }
}

