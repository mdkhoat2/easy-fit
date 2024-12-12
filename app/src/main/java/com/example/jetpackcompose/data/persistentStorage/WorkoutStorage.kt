package com.example.jetpackcompose.data.persistentStorage

import android.content.Context
import com.example.jetpackcompose.data.dataModel.DayOfWeek
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.ExerciseName
import com.example.jetpackcompose.data.dataModel.ExerciseType
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.WeekSummary
import com.example.jetpackcompose.data.dataModel.Workout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object PersistentStorageManager {
    private const val WORKOUTS_FILE_NAME = "workouts.json"
    private const val STREAK_FILE_NAME = "streak.json"
    private val gson = Gson()

    private var patchHistory : PatchHistory? = null
    private val workouts = mutableListOf<Workout>()
    private var streak: Int = 0


    // **Workouts Management**
    fun fillWithMockData() {
        workouts+=(0..3).map {
            when (it) {
                0 -> Workout(
                    id = "workout_1234",
                    name = "All Rounder",
                    exercises = listOf(
                        Exercise(
                            name = ExerciseName.HAND_GRIP,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                        ),
                        Exercise(
                            name = ExerciseName.SIT_UP,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                        ),
                        Exercise(
                            name = ExerciseName.PUSH_UP,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 0,
                        ),
                    ),
                    duration = 30,
                    creatorId = null,
                )
                1 -> Workout(
                    id = "workout_5678",
                    name = "Cardio Blast",
                    exercises = listOf(
                        Exercise(
                            name = ExerciseName.JUMPING_ROPE,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                        ),
                        Exercise(
                            name = ExerciseName.JUMPING_ROPE,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                        ),
                        Exercise(
                            name = ExerciseName.JUMPING_ROPE,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                        ),
                    ),
                    duration = 90,
                    creatorId = null,
                )
                2 -> Workout(
                    id = "workout_9101",
                    name = "Yoga",
                    exercises = listOf(
                        Exercise(
                            name = ExerciseName.YOGA,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                        ),
                        Exercise(
                            name = ExerciseName.YOGA,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                        ),
                        Exercise(
                            name = ExerciseName.YOGA,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                        ),
                    ),
                    duration = 90,
                    creatorId = null,
                )
                3 -> Workout(
                    id = "workout_1213",
                    name = "Weightlifting",
                    exercises = listOf(
                        Exercise(
                            name = ExerciseName.WEIGHTLIFTING,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                        ),
                        Exercise(
                            name = ExerciseName.WEIGHTLIFTING,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                        ),
                        Exercise(
                            name = ExerciseName.WEIGHTLIFTING,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                        ),
                    ),
                    duration = 30,
                    creatorId = null,
                )
                else -> throw IllegalStateException("Unexpected index: $it")
            }
        }

        streak = 0

        patchHistory =PatchHistory(
            weeks = listOf(
                WeekSummary(
                    planId = "plan_1234",
                    startDate = "2024-12-01",
                    missedSessions = 2,
                    totalTime = 120,
                    sessionCount = 5,
                    missedDays = listOf(
                        DayOfWeek.MONDAY,
                        DayOfWeek.WEDNESDAY,
                    ),
                ),
                WeekSummary(
                    planId = "plan_5678",
                    startDate = "2024-12-08",
                    missedSessions = 1,
                    totalTime = 90,
                    sessionCount = 4,
                    missedDays = listOf(
                        DayOfWeek.FRIDAY,
                    ),
                ),
                WeekSummary(
                    planId = "plan_9101",
                    startDate = "2024-12-15",
                    missedSessions = 0,
                    totalTime = 150,
                    sessionCount = 6,
                    missedDays = emptyList(),
                ),
            ),
        )
    }


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

    // **Patch History Management**
    fun savePatchHistoryToFile(context: Context) {
        val file = File(context.filesDir, STREAK_FILE_NAME)
        file.writeText(gson.toJson(patchHistory))
    }

    fun loadPatchHistoryFromFile(context: Context): PatchHistory? {
        val file = File(context.filesDir, STREAK_FILE_NAME)
        if (file.exists()) {
            val type = object : TypeToken<PatchHistory>() {}.type
            patchHistory = gson.fromJson(file.readText(), type)
        }
        return patchHistory
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
