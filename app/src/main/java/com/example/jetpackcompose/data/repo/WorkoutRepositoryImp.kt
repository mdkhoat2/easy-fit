package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.dataModel.*
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.domain.repo.WorkoutRepository

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
                        planId = "plan_${(1000..9999).random()}",
                        startDate = "2024-12-${(1..28).random()}",
                        missedSessions = (0..5).random(),
                        totalTime = (60..300).random(),
                        sessionCount = (0..7).random(),
                        missedDays = List((0..7).random()) { DayOfWeek.values().random() },
                    )
                },
        )

    // override suspend fun getMonthlyHistory(userId: String): List<WeeklyHistory> {
    //     return List(4) { getWeeklyHistory(userId, cnt = 1, skip = 0) }
    // }

    override suspend fun getYourWorkouts(userId: String): List<Workout> =
        List(5) {
            Workout(
                id = "workout_${(1000..9999).random()}",
                name = "Workout ${(1..5).random()}",
                exercises =
                    List((1..5).random()) {
                        Exercise(
                            name = ExerciseName.values().random(),
                            type = ExerciseType.values().random(),
                            repetition = (10..50).random(),
                            duration = (30..120).random(),
                            restTime = (10..30).random(),
                        )
                    },
                duration = (15..90).random(),
                creatorId = null,
            )
        }

    override suspend fun updateWorkout(
        workoutId: String,
        updatedWorkout: Workout,
    ): Boolean = true

    override suspend fun createWorkout(workout: Workout): Boolean = (0..1).random() == 1

    override suspend fun deleteWorkout(workoutId: String): Boolean = true
}
