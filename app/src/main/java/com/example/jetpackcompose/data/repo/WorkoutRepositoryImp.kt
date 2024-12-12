package com.example.jetpackcompose.data.repo

import android.content.Context
import com.example.jetpackcompose.data.dataModel.*
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.domain.repo.WorkoutRepository

class WorkoutRepositoryImp(
    private val database: WorkoutDatabase,
) : WorkoutRepository {

    override suspend fun getYourWorkouts(): List<Workout> = database.getAllWorkouts()

    override suspend fun getWorkoutById(workoutId: String): Workout? = database.getWorkoutById(workoutId)

    override suspend fun getExerciseFromWorkout(workoutId: String): List<Exercise> {
        return database.getExerciseFromWorkout(workoutId)
    }

    override suspend fun getWorkoutStreak(): Int
    {
        return database.getWorkoutStreak()
    }
    override suspend fun resetWorkoutStreak(): Boolean {
        database.updateWorkoutStreak(0)
        return true
    }

    override suspend fun addWorkoutStreak(): Boolean
    {
        database.updateWorkoutStreak(database.getWorkoutStreak() + 1)
        return true
    }

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

//    override suspend fun getPatchHistory(cnt: Int, skip: Int): PatchHistory {
//        return PatchHistory()
//    }
}

object WorkoutRepositoryProvider {
    private var repositoryInstance: WorkoutRepository? = null

    fun getRepository(context: Context): WorkoutRepository {
        return repositoryInstance ?: synchronized(this) {
            repositoryInstance ?: WorkoutRepositoryImp(
                database = WorkoutDatabase.getInstance(context.applicationContext)
            ).also { repositoryInstance = it }
        }
    }
}
