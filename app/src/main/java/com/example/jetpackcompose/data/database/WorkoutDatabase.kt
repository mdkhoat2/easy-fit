package com.example.jetpackcompose.data.database

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
import com.example.jetpackcompose.data.persistentStorage.PersistentStorageManager
import com.example.jetpackcompose.util.resetForFirstTimeUser

class WorkoutDatabase private constructor() {
    // Local database implementation

    companion object {
        @Volatile
        private var instance: WorkoutDatabase? = null

        suspend fun getInstance(context: Context): WorkoutDatabase {
            return instance ?: synchronized(this) {
                instance ?: WorkoutDatabase().also { db ->
                    instance = db
                }
            }.also { db ->
                db.initialize(context.applicationContext)
            }
        }
    }

    // **Initialize the database asynchronously**
    private suspend fun initialize(context: Context) {
        cachedWorkouts = PersistentStorageManager.loadWorkouts(context)
        cachedPatchHistory = PersistentStorageManager.loadMissedWeeks(context)
        cachedPlan = PersistentStorageManager.loadPlan(context)
        cachedCustomExercise = PersistentStorageManager.loadCustomExercises(context)
        // for testing uncomment the line below and comment the line above
//        fillWithSampleData()
//        PersistentStorageManager.saveMissedWeeks(context, cachedPatchHistory)
//        PersistentStorageManager.saveWorkouts(context, cachedWorkouts)
//        PersistentStorageManager.savePlan(context, cachedPlan)
//        PersistentStorageManager.saveCustomExercises(context, cachedCustomExercise)
//        resetForFirstTimeUser(context)
    }

    private var cachedPatchHistory: PatchHistory = PatchHistory(emptyList())
    private var cachedWorkouts: List<Workout> = emptyList()
    private lateinit var cachedPlan: Plan
    private var cachedCustomExercise: List<Exercise> = emptyList()

    /**
     *  |  WORKOUTS
     */

    fun getAllWorkouts(): List<Workout> = cachedWorkouts

    suspend fun addWorkout(context: Context, workout: Workout) {
        cachedWorkouts = cachedWorkouts + workout
        PersistentStorageManager.saveWorkouts(context, cachedWorkouts)
    }

    suspend fun editWorkout(context: Context, id: String, workout: Workout): Boolean {
        val index = cachedWorkouts.indexOfFirst { it.id == id }
        if (index == -1) return false
        cachedWorkouts = cachedWorkouts.toMutableList().apply { set(index, workout) }
        PersistentStorageManager.saveWorkouts(context, cachedWorkouts)
        return true
    }

    suspend fun deleteWorkout(context: Context, workoutId: String): Boolean {
        val index = cachedWorkouts.indexOfFirst { it.id == workoutId }
        if (index == -1) return false
        cachedWorkouts = cachedWorkouts.toMutableList().apply { removeAt(index) }
        PersistentStorageManager.saveWorkouts(context, cachedWorkouts)
        return true
    }

    fun getWorkoutById(workoutId: String): Workout? {
        return cachedWorkouts.find { it.id == workoutId }
    }

    fun getExerciseFromWorkout(workoutId: String): List<Exercise> {
        return cachedWorkouts.find { it.id == workoutId }?.exercises ?: emptyList()
    }

    /**
     *  |  PATCH HISTORY
     */

    fun getPatchHistory(): PatchHistory = cachedPatchHistory

    suspend fun updatePatchHistory(context: Context, patchHistory: PatchHistory) {
        cachedPatchHistory = patchHistory
        PersistentStorageManager.saveMissedWeeks(context, cachedPatchHistory)
        Log.d("WorkoutDatabase", "Updated patch history: $cachedPatchHistory")
    }

    /**     |
     * PLAN V
     */

    suspend fun updatePlan(context: Context, plan: Plan): Boolean {
        cachedPlan = plan
        PersistentStorageManager.savePlan(context, cachedPlan)
        return true
    }
    fun getPlan(): Plan = cachedPlan

    /**
     *  |  CUSTOM EXERCISE
     */

    fun getCustomExercise(): List<Exercise> = cachedCustomExercise

    suspend fun updateCustomExercise(context: Context, customAction: List<Exercise>): Boolean {
        cachedCustomExercise = customAction
        PersistentStorageManager.saveCustomExercises(context, cachedCustomExercise)
        Log.d("WorkoutDatabase", "Updated custom exercises: $cachedCustomExercise")
        return true
    }


    private fun fillWithSampleData()
    {
        cachedWorkouts+=(0..4).map {
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
                            description = "Squeeze the hand grip"
                        ),
                        Exercise(
                            name = ExerciseName.SIT_UP,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                            description = "Lay on your back and sit up"
                        ),
                        Exercise(
                            name = ExerciseName.PUSH_UP,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 0,
                            description = "Push up from the ground"
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
                            description = "Do jumping rope"
                        ),
                        Exercise(
                            name = ExerciseName.JUMPING_ROPE,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                            description = "Do jumping rope"
                        ),
                        Exercise(
                            name = ExerciseName.JUMPING_ROPE,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                            description = "Do jumping rope"
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
                            description = "Do yoga"
                        ),
                        Exercise(
                            name = ExerciseName.YOGA,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                            description = "Do yoga"
                        ),
                        Exercise(
                            name = ExerciseName.YOGA,
                            type = ExerciseType.TIMED,
                            repetition = 0,
                            duration = 30,
                            restTime = 10,
                            description = "Do yoga"
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
                            description = "Lift weights"
                        ),
                        Exercise(
                            name = ExerciseName.WEIGHTLIFTING,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                            description = "Lift weights"
                        ),
                        Exercise(
                            name = ExerciseName.WEIGHTLIFTING,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                            description = "Lift weights"
                        ),
                    ),
                    duration = 30,
                    creatorId = null,
                )
                4 -> Workout( // custom
                    id = "workout_1415",
                    name = "Custom Workout",
                    exercises = listOf(
                        Exercise(
                            name = ExerciseName.CUSTOM,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                            description = "Custom exercise 1",
                            customName = "Duck Sicking"
                        ),
                        Exercise(
                            name = ExerciseName.CUSTOM,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                            description = "Custom exercise 2",
                            customName = "Back Flip"
                        ),
                        Exercise(
                            name = ExerciseName.CUSTOM,
                            type = ExerciseType.COUNTED,
                            repetition = 10,
                            duration = 0,
                            restTime = 5,
                            description = "Custom exercise 3",
                            customName = "Hand Stand"
                        ),
                    ),
                    duration = 30,
                    creatorId = null,

                )
                else -> throw IllegalStateException("Unexpected index: $it")
            }
        }

        cachedPatchHistory =PatchHistory(
            weeks = listOf(
                WeekSummary(
                    startDate = "2024-10-21",
                    missedSessions = 0,
                    totalTime = 45f,
                    sessionCount = 2,
                    missedDays = listOf(DayOfWeek.THURSDAY),
                ),
                WeekSummary(
                    startDate = "2024-11-04",
                    missedSessions = 1,
                    totalTime = 72f,
                    sessionCount = 3,
                    missedDays = listOf(
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.FRIDAY,
                    ),
                ),
                WeekSummary(
                    startDate = "2024-11-18",
                    missedSessions = 1,
                    totalTime = 90f,
                    sessionCount = 4,
                    missedDays = listOf(
                        DayOfWeek.WEDNESDAY,
                    ),
                ),
                WeekSummary(
                    startDate = "2024-12-02",
                    missedSessions = 2,
                    totalTime = 123f,
                    sessionCount = 5,
                    missedDays = listOf(
                        DayOfWeek.MONDAY,
                        DayOfWeek.WEDNESDAY,
                    ),
                ),
                WeekSummary(
                    startDate = "2024-12-16",
                    missedSessions = 0,
                    totalTime = 157f,
                    sessionCount = 6,
                    missedDays = emptyList(),
                ),
                // Add more weeks here of this week
                WeekSummary(
                    startDate = "2024-12-30",
                    missedSessions = 0,
                    totalTime = 157f,
                    sessionCount = 6,
                    missedDays = listOf(DayOfWeek.MONDAY,DayOfWeek.WEDNESDAY),
                ),

            ),
        )

        cachedPlan = Plan(name = "Beginner",dateWorkout = listOf(DayOfWeek.MONDAY,DayOfWeek.WEDNESDAY,DayOfWeek.FRIDAY,),
            timeWorkout = listOf("20:00","20:00","20:00"),maxMissDay = 3,minSession = 10,minHour = 4f)

        cachedCustomExercise = listOf(
            Exercise(
                name = ExerciseName.CUSTOM,
                description = "Custom exercise 1",
                customName = "Duck Sicking"
            ),
            Exercise(
                name = ExerciseName.CUSTOM,
                description = "Custom exercise 2",
                customName = "Back Flip"
            ),
            Exercise(
                name = ExerciseName.CUSTOM,
                description = "Custom exercise 3",
                customName = "Hand Stand"
            ),
        )
    }
}

