package com.example.jetpackcompose.data.repo

import android.content.Context
import android.util.Log
import com.example.jetpackcompose.data.dataModel.*
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.domain.repo.WorkoutRepository
import com.example.jetpackcompose.util.getStartOfWeek
import java.time.LocalDate

class WorkoutRepositoryImp(
    private val database: WorkoutDatabase,
    private val context: Context
) : WorkoutRepository {
    override suspend fun getYourWorkouts(): List<Workout> = database.getAllWorkouts()

    override suspend fun getWorkoutById(workoutId: String): Workout? = database.getWorkoutById(workoutId)

    override suspend fun getExerciseFromWorkout(workoutId: String): List<Exercise> {
        return database.getExerciseFromWorkout(workoutId)
    }

    override suspend fun getWorkoutStreak(): Int {return database.getWorkoutStreak()}
    override suspend fun resetWorkoutStreak(){database.updateWorkoutStreak(context,0)}
    override suspend fun addWorkoutStreak(){database.updateWorkoutStreak(context,database.getWorkoutStreak() + 1)}

    override suspend fun updateWorkout(
        workoutId: String,
        updatedWorkout: Workout,
    ): Boolean {
        return database.editWorkout(context,workoutId,updatedWorkout)
    }

    override suspend fun createWorkout(workout: Workout): Boolean
    {
        database.addWorkout(context,workout)
        return true
    }

    override suspend fun deleteWorkout(workoutId: String): Boolean
    {
        database.deleteWorkout(context,workoutId)
        return true
    }

    override suspend fun getPatchHistory(cnt: Int, skip: Int): PatchHistory {
        val fullHistory = database.getPatchHistory()

        if (cnt < 1 || skip < 0) {
            throw IllegalArgumentException("Both cnt and skip must be non-negative")
        }

        val startOfCurrentWeek = getStartOfWeek(LocalDate.now())
        val startWeek = startOfCurrentWeek.minusWeeks((cnt + skip-1).toLong())
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
                        totalTime = 0f,
                        sessionCount = 0,
                        missedDays = emptyList()
                    )
                )
            }
            currentWeek = currentWeek.plusWeeks(1)
        }

        return PatchHistory(allWeeks.take(cnt))
    }

    override suspend fun addSessionToHistory(duraMilis:Float): Boolean {
        val fullHistory = database.getPatchHistory()
        val duraMin = duraMilis/60000f

        val updatedHistory : PatchHistory

        // check if the last week is the current week
        if (checkIfWeekIsCurrentWeek(fullHistory.weeks.last(), LocalDate.now()))
        {
            val currentWeek = fullHistory.weeks.last()
            val updatedWeek = currentWeek.copy(
                sessionCount = currentWeek.sessionCount + 1,
                totalTime = currentWeek.totalTime + duraMin
            )

            updatedHistory = fullHistory.copy(
                weeks = fullHistory.weeks.dropLast(1) + updatedWeek
            )
        } else {
            val newWeek = WeekSummary(
                startDate = getStartOfWeek(LocalDate.now()).toString(),
                missedSessions = 0,
                totalTime = duraMin,
                sessionCount = 1,
                missedDays = emptyList()
            )

            updatedHistory = fullHistory.copy(
                weeks = fullHistory.weeks + newWeek
            )
        }
        database.updatePatchHistory(context,updatedHistory)

        return true
    }

    override suspend fun addMissedDayToHistory(): Boolean {
        val fullHistory = database.getPatchHistory()

        // Calculate yesterday's date
        val yesterday = LocalDate.now().minusDays(1)
        val toDate = dateToDayOfWeek(yesterday)

        // Check if the week of yesterday is the current week
        if (checkIfWeekIsCurrentWeek(fullHistory.weeks.last(), yesterday)) {
            val currentWeek = fullHistory.weeks.last()
            if (currentWeek.missedDays.contains(toDate)) return false

            // Update the current week's missed sessions and days
            val updatedWeek = currentWeek.copy(
                missedSessions = currentWeek.missedSessions + 1,
                missedDays = currentWeek.missedDays + toDate
            )

            // Replace the last week with the updated week in the history
            val updatedHistory = fullHistory.copy(
                weeks = fullHistory.weeks.dropLast(1) + updatedWeek
            )
            database.updatePatchHistory(context, updatedHistory)
            return true
        } else {
            val newWeek = WeekSummary(
                startDate = getStartOfWeek(yesterday).toString(),
                missedSessions = 1,
                totalTime = 0f,
                sessionCount = 0,
                missedDays = listOf(toDate)
            )

            val updatedHistory = fullHistory.copy(
                weeks = fullHistory.weeks + newWeek
            )
            database.updatePatchHistory(context, updatedHistory)
            return true
        }
    }


    private fun checkIfWeekIsCurrentWeek(week: WeekSummary, theDate: LocalDate): Boolean {
        val startOfTheWeek = getStartOfWeek(theDate)
        return week.startDate == startOfTheWeek.toString()
    }

    override suspend fun getPlan(): Plan {
        return database.getPlan()
    }

    override suspend fun updatePlan(plan: Plan): Boolean {
        return database.updatePlan(context,plan)
    }
}
