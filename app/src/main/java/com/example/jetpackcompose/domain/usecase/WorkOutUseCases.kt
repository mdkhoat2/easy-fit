package com.example.jetpackcompose.domain.usecase

import android.content.Context
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.PatchHistory
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.data.dataModel.dateToDayOfWeek
import com.example.jetpackcompose.domain.repo.WorkoutRepository
import com.example.jetpackcompose.util.getLastDate
import com.example.jetpackcompose.util.saveLastDate
import java.time.LocalDate


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

class AddDayToHistoryUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke(duraMilis:Float): Boolean {

        return repository.addSessionToHistory( duraMilis)
    }
}

class AddMissedDaysToHistoryUseCase(private val repository: WorkoutRepository) {

    suspend operator fun invoke(context: Context) : Boolean {
        // Get the last workout date
        val lastWorkoutDate = getLastDate(context).takeIf { it.isNotBlank() }
            ?.let { LocalDate.parse(it) } ?: LocalDate.now().minusDays(1)

        // Calculate the range of dates to check
        val yesterday = LocalDate.now().minusDays(1)
        if (!lastWorkoutDate.isBefore(yesterday)) return false// No dates to check

        // Get the plan
        val plan = repository.getPlan()

        // Iterate over the date range
        val missedDates = mutableListOf<LocalDate>()
        var currentDate = lastWorkoutDate.plusDays(1)
        while (!currentDate.isAfter(yesterday)) {
            val toDate = dateToDayOfWeek(currentDate)
            if (plan.dateWorkout.contains(toDate)) {
                missedDates.add(currentDate)
            }
            currentDate = currentDate.plusDays(1)
        }

        // Update missed days in history
        if (missedDates.isNotEmpty()) {
            repository.addMissedDaysToHistory(missedDates)
        }

        // Save the current date as the last workout date
        saveLastDate(context,LocalDate.now().minusDays(1))

        return missedDates.isNotEmpty()
    }
}




class GetPlanUseCase(private val repository: WorkoutRepository) {
    suspend operator fun invoke() = repository.getPlan()
}






