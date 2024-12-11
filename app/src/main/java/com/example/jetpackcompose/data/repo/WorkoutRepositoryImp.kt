package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.dataModel.*
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.domain.repo.WorkoutRepository
import kotlin.random.Random

class WorkoutRepositoryImp(
    private val api: WorkoutApi,
    private val database: WorkoutDatabase,
) : WorkoutRepository {
    override suspend fun getPatchHistory(
        userId: String,
        cnt: Int,
        skip: Int,
    ): PatchHistory =
        PatchHistory(
            weeks =
                List(cnt) {
                    WeekSummary(
                        planId = "plan_${Random.nextInt(1000, 9999)}",
                        startDate = "2024-12-${Random.nextInt(1, 28)}",
                        missedSessions = Random.nextInt(0, 5),
                        totalTime = Random.nextInt(60, 300),
                        sessionCount = Random.nextInt(0, 7),
                        missedDays = List(Random.nextInt(0, 7)) { DayOfWeek.values().random() },
                    )
                },
        )

    override suspend fun getYourWorkouts(): List<Workout> =
        List(4) { // Hardcoded values for testing // 4 workouts
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


    override suspend fun getWorkoutById(workoutId: String): Workout =
        Workout(
            id = workoutId,
            name = "Workout ${Random.nextInt(1, 100)}",
            exercises =
                List(Random.nextInt(1, 5)) {
                    Exercise(
                        name = ExerciseName.values().random(),
                        type = ExerciseType.values().random(),
                        repetition = Random.nextInt(10, 50),
                        duration = Random.nextInt(30, 120),
                        restTime = Random.nextInt(10, 30),
                    )
                },
            duration = Random.nextInt(15, 90),
            creatorId = null,
        )

    override suspend fun getWorkoutStreak(): Int = Random.nextInt(0, 100)
    override suspend fun resetWorkoutStreak(): Boolean = true
    override suspend fun addWorkoutStreak(): Boolean = true

    override suspend fun updateWorkout(
        workoutId: String,
        updatedWorkout: Workout,
    ): Boolean = true

    override suspend fun createWorkout(workout: Workout): Boolean = true

    override suspend fun deleteWorkout(workoutId: String): Boolean = true
}

object WorkoutRepositoryProvider {
    private val workoutApi = WorkoutApi() // Create WorkoutApi instance
    private val workoutDatabase = WorkoutDatabase() // Create WorkoutDatabase instance

    val repository: WorkoutRepository by lazy {
        WorkoutRepositoryImp(
            api = workoutApi,
            database = workoutDatabase
        )
    }
}
