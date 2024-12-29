package com.example.jetpackcompose.data.persistentStorage

import android.content.Context
import android.util.Log
import com.example.jetpackcompose.data.dataModel.DayOfWeek
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.ExerciseName
import com.example.jetpackcompose.data.dataModel.ExerciseType
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.Plan
import com.example.jetpackcompose.data.dataModel.WeekSummary
import com.example.jetpackcompose.data.dataModel.Workout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

object PersistentStorageManager {
    private const val WORKOUTS_FILE_NAME = "workouts.json"
    private const val STREAK_FILE_NAME = "streak.json"
    private const val MISSED_WEEKS_FILE_NAME = "missed_weeks.json"
    private const val PLAN_FILE_NAME = "plan.json"
    private const val CUSTOM_EXERCISES_FILE_NAME = "custom_exercises.json"
    private val gson = Gson()

    // Generic save function

    private suspend inline fun <reified T> saveToFile(context: Context, fileName: String, data: T) {
        withContext(Dispatchers.IO) { // Perform the operation on a background thread
            val file = File(context.filesDir, fileName)
            file.writeText(gson.toJson(data))
        }
    }

    // Generic load function with a default value
    private suspend inline fun <reified T> loadFromFile(context: Context, fileName: String, defaultValue: T): T {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                gson.fromJson(file.readText(), object : TypeToken<T>() {}.type)
            } else defaultValue
        }
    }

    suspend fun saveWorkouts(context: Context, workouts: List<Workout>) {
        saveToFile(context, WORKOUTS_FILE_NAME, workouts)
        Log.d("PersistentStorageManager", "Saved workouts: $workouts")
    }

    suspend fun loadWorkouts(context: Context): List<Workout> {
        return loadFromFile(context, WORKOUTS_FILE_NAME, emptyList())
    }

    suspend fun saveStreak(context: Context, streak: Int) {
        saveToFile(context, STREAK_FILE_NAME, streak)
    }

    suspend fun loadStreak(context: Context): Int {
        return loadFromFile(context, STREAK_FILE_NAME, 0)
    }

    suspend fun saveMissedWeeks(context: Context, patchHistory: PatchHistory) {
        saveToFile(context, MISSED_WEEKS_FILE_NAME, patchHistory)
    }

    suspend fun loadMissedWeeks(context: Context): PatchHistory {
        return loadFromFile(context, MISSED_WEEKS_FILE_NAME, PatchHistory(emptyList()))
    }

    suspend fun savePlan(context: Context, plan: Plan) {
        saveToFile(context, PLAN_FILE_NAME, plan)
    }

    suspend fun loadPlan(context: Context): Plan {
        return loadFromFile(context, PLAN_FILE_NAME,
            Plan(
                name = "No Plan",
                dateWorkout = listOf(),
                timeWorkout = listOf(),
                maxMissDay = 0,
                minSession = 0,
                minHour = 0f
            ))
    }

    suspend fun saveCustomExercises(context: Context, customExercises: List<Exercise>) {
        saveToFile(context, CUSTOM_EXERCISES_FILE_NAME, customExercises)
    }

    suspend fun loadCustomExercises(context: Context): List<Exercise> {
        return loadFromFile(context, CUSTOM_EXERCISES_FILE_NAME, emptyList())
    }
}
