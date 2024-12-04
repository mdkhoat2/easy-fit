package com.example.jetpackcompose.domain.usecase

import com.example.jetpackcompose.data.dataModel.WeeklyHistory
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.domain.repo.WorkoutRepository


class GetYourWorkoutsUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(userId: String): List<Workout> {
        return repository.getYourWorkouts(userId)
    }
}

class EditWorkoutUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(workoutId: String, updatedWorkout: Workout): Boolean {
        return repository.updateWorkout(workoutId, updatedWorkout)
    }
}

class CreateWorkoutUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(workout: Workout): Boolean {
        return repository.createWorkout(workout)
    }
}




