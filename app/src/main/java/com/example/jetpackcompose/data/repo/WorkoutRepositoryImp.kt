package com.example.jetpackcompose.data.repo

import android.content.Context
import com.example.jetpackcompose.data.dataModel.*
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.domain.repo.WorkoutRepository
import com.example.jetpackcompose.util.getStartOfCurrentWeek
import java.time.LocalDate

class WorkoutRepositoryImp(
    private val database: WorkoutDatabase,
) : WorkoutRepository {
    override suspend fun getYourWorkouts(): List<Workout> = database.getAllWorkouts()

    override suspend fun getWorkoutById(workoutId: String): Workout? = database.getWorkoutById(workoutId)

    override suspend fun getExerciseFromWorkout(workoutId: String): List<Exercise> {
        return database.getExerciseFromWorkout(workoutId)
    }

    override suspend fun getWorkoutStreak(): Int {return database.getWorkoutStreak()}
    override suspend fun resetWorkoutStreak(){database.updateWorkoutStreak(0)}
    override suspend fun addWorkoutStreak(){database.updateWorkoutStreak(database.getWorkoutStreak() + 1)}

    override suspend fun updateWorkout(
        workoutId: String,
        updatedWorkout: Workout,
    ): Boolean {
        return database.editWorkout(workoutId,updatedWorkout)
    }

    override suspend fun createWorkout(workout: Workout): Boolean
    {
        database.addWorkout(workout)
        return true
    }

    override suspend fun deleteWorkout(workoutId: String): Boolean
    {
        return true
    }

    override suspend fun getPatchHistory(cnt: Int, skip: Int): PatchHistory {
        val fullHistory = database.getPatchHistory()

        if (cnt < 0 || skip < 0) {
            throw IllegalArgumentException("Both cnt and skip must be non-negative")
        }

        val startOfCurrentWeek = getStartOfCurrentWeek()
        val startWeek = startOfCurrentWeek.minusWeeks((cnt + skip).toLong())
        val endWeek = startOfCurrentWeek.minusWeeks(skip.toLong())

        // Filter the weeks within the range
        val weeksInRange = fullHistory.weeks.filter { week ->
            val weekDate = LocalDate.parse(week.startDate)
            !weekDate.isBefore(startWeek) && !weekDate.isAfter(endWeek)
        }

        // Add empty weeks between non-adjacent weeks
        val allWeeks = mutableListOf<WeekSummary>()
        var currentWeek = startWeek

        while (!currentWeek.isAfter(endWeek)) {
            val matchingWeek = weeksInRange.find { week ->
                LocalDate.parse(week.startDate) == currentWeek
            }

            if (matchingWeek != null) {
                allWeeks.add(matchingWeek)
            } else {
                allWeeks.add(
                    WeekSummary(
                        startDate = currentWeek.toString(),
                        missedSessions = 0,
                        totalTime = 0,
                        sessionCount = 0,
                        missedDays = emptyList()
                    )
                )
            }
            currentWeek = currentWeek.plusWeeks(1)
        }

        return PatchHistory(allWeeks.take(cnt))
    }


    override suspend fun addWeekToHistory(weekSummary: WeekSummary): Boolean {
        val history = database.getPatchHistory()

        return true
    }
}
