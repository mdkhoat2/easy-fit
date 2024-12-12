package com.example.jetpackcompose.util
import android.content.Context
import com.example.jetpackcompose.data.dataModel.Workout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class WorkoutFileStorage(private val context: Context) {
    private val gson = Gson()
    private val WORKOUT_FILE_NAME = "workouts.json"

    fun saveWorkouts(workouts: List<Workout>) {
        val jsonString = gson.toJson(workouts)
        File(context.filesDir, WORKOUT_FILE_NAME).writeText(jsonString)
    }

    fun loadWorkouts(): List<Workout> {
        val file = File(context.filesDir, WORKOUT_FILE_NAME)
        if (!file.exists()) return emptyList()
        val jsonString = file.readText()
        val type = object : TypeToken<List<Workout>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}
