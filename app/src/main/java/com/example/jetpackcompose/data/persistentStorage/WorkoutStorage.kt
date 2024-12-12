package com.example.jetpackcompose.data.persistentStorage

import android.content.Context
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.Workout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object PersistentStorageManager {
    private const val WORKOUTS_FILE_NAME = "workouts.json"
    private const val STREAK_FILE_NAME = "streak.json"
    private val gson = Gson()

    private var patchHistory : PatchHistory? = null
    private val workouts: MutableList<Workout> = mutableListOf()
    private var streak: Int = 0

    // **Workouts Management**
    fun saveWorkoutsToFile(context: Context) {
        val file = File(context.filesDir, WORKOUTS_FILE_NAME)
        file.writeText(gson.toJson(workouts))
    }

    fun loadWorkoutsFromFile(context: Context): List<Workout> {
        val file = File(context.filesDir, WORKOUTS_FILE_NAME)
        if (file.exists()) {
            val type = object : TypeToken<List<Workout>>() {}.type
            workouts.clear()
            workouts.addAll(gson.fromJson(file.readText(), type))
        }
        return workouts
    }

    // **Streak Management**
    fun saveStreakToFile(context: Context) {
        val file = File(context.filesDir, STREAK_FILE_NAME)
        file.writeText(streak.toString())
    }

    fun loadStreakFromFile(context: Context): Int {
        val file = File(context.filesDir, STREAK_FILE_NAME)
        if (file.exists()) {
            streak = file.readText().toInt()
        }
        return streak
    }

    // Expose current state
    fun getWorkouts(): List<Workout> = workouts
    fun getStreak(): Int = streak
    fun setStreak(value: Int) {
        streak = value
    }

    fun setPatchHistory(value: PatchHistory) {
        patchHistory = value
    }

    fun getPatchHistory(): PatchHistory? = patchHistory
}
