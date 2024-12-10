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

    // override suspend fun getMonthlyHistory(userId: String): List<WeeklyHistory> {
    //     return List(4) { getWeeklyHistory(userId, cnt = 1, skip = 0) }
    // }

    override suspend fun getYourWorkouts(): List<Workout> =
        List(20) {
            Workout(
                id = "workout_${Random.nextInt(1000, 9999)}",
                name = "Workout ${Random.nextInt(1, 100)}",
                exercises =
                    listOf(
                        Exercise(
                            name = ExerciseName.values().random(),
                            type = ExerciseType.values().random(),
                            repetition = Random.nextInt(10, 50),
                            duration = Random.nextInt(30, 120),
                            restTime = Random.nextInt(10, 30),
                            iconId = Random.nextInt(1, 10),
                        ),
                    ),
                duration = Random.nextInt(15, 90),
                creatorId = null,
            )
        }

    override suspend fun updateWorkout(
        workoutId: String,
        updatedWorkout: Workout,
    ): Boolean = true

    override suspend fun createWorkout(workout: Workout): Boolean = true

    override suspend fun deleteWorkout(workoutId: String): Boolean = true
}
