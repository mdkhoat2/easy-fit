package com.example.jetpackcompose.domain.usecase

import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.WeekSummary
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.domain.repo.WorkoutRepository


class GetYourWorkoutsUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(): List<Workout> {

        return repository.getYourWorkouts()
    }
}

class GetWorkoutByIdUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(workoutId: String): Workout? {
        return repository.getWorkoutById(workoutId)
    }
}

class EditWorkoutUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(workoutId: String, updatedWorkout: Workout): Boolean {
        Validator.validateNonEmpty(workoutId, "Workout ID")
        return repository.updateWorkout(workoutId, updatedWorkout)
    }
}

class CreateWorkoutUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(workout: Workout): Boolean {
        Validator.validateNonEmptyList(workout.exercises, "Exercises")

        return repository.createWorkout(workout)
    }
}

class GetExerciseFromWorkoutUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(workoutId: String): List<Exercise> {
        return repository.getExerciseFromWorkout(workoutId)
    }
}

class DeleteWorkoutUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(workoutId: String): Boolean {
        return repository.deleteWorkout(workoutId)
    }
}

class GetPatchHistoryUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(cnt:Int=12,skip:Int=0): PatchHistory? {

        return repository.getPatchHistory(cnt,skip)
    }
}

class AddWeekToHistoryUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(weekSummary: WeekSummary): Boolean {
        return repository.addWeekToHistory(weekSummary)
    }
}

class GetPlanUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke() = repository.getPlan()
}





