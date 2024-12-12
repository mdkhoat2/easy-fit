package com.example.jetpackcompose.data.repo

import com.example.jetpackcompose.data.dataModel.*
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.domain.repo.WorkoutRepository

class WorkoutRepositoryImp(
    private val database: WorkoutDatabase,
) : WorkoutRepository {

<<<<<<< Updated upstream
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
                            restTime = Random.nextInt(10, 30)
                        ),
                    ),
                duration = Random.nextInt(15, 90),
                creatorId = null,
            )
        }

=======
    override suspend fun getYourWorkouts(): List<Workout> = database.getAllWorkouts()

    override suspend fun getWorkoutById(workoutId: String): Workout? = database.getWorkoutById(workoutId)

    override suspend fun getWorkoutStreak(): Int
    {
        return database.getWorkoutStreak()
    }
    override suspend fun resetWorkoutStreak(): Boolean {
        database.resetWorkoutStreak()
        return true
    }

    override suspend fun addWorkoutStreak(): Boolean
    {
        database.updateWorkoutStreak(database.getWorkoutStreak() + 1)
        return true
    }

>>>>>>> Stashed changes
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
}
<<<<<<< Updated upstream
=======

object WorkoutRepositoryProvider {
    private val workoutDatabase = WorkoutDatabase.getInstance()

    val repository: WorkoutRepository by lazy {
        WorkoutRepositoryImp(database = workoutDatabase)
    }
}
>>>>>>> Stashed changes
