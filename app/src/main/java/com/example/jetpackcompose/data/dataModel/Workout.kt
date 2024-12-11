package com.example.jetpackcompose.data.dataModel

import com.example.jetpackcompose.R

enum class ExerciseName {
    HAND_GRIP,
    JUMPING_ROPE,
    PUSH_UP,
    SIT_UP,
    WEIGHTLIFTING,
    YOGA
}

enum class ExerciseType {
    COUNTED, TIMED
}

data class Workout(
    val id: String,
    val creatorId: User?,
    val name: String,
    val exercises: List<Exercise>,
    val duration: Int // Total duration in minutes
)

data class Exercise(
    val name: ExerciseName,
    val type: ExerciseType = ExerciseType.COUNTED,
    val repetition: Int = 10, // Number of repetitions for "counted" exercises
    val duration: Int = 0, // Duration in seconds for "timed" exercises
    val restTime: Int = 15, // Rest time between sets in seconds
)

data class WorkoutSummary( //local data model
    val streak: Int,
    val exercisesCompleted: Int,
    val duration: Int, // In minutes
    val exercises: List<ExerciseDetail>
)

data class ExerciseDetail(
    val name: String,
    val sets: Int,
    val reps: Int,
    val duration: Int // Optional (for timed exercises)
)

fun getExerciseString(exercise:Exercise): String {
    return when(exercise.name) {
        ExerciseName.HAND_GRIP -> "Hand Grip"
        ExerciseName.JUMPING_ROPE -> "Jumping Rope"
        ExerciseName.PUSH_UP -> "Push Up"
        ExerciseName.SIT_UP -> "Sit Up"
        ExerciseName.WEIGHTLIFTING -> "Weightlifting"
        ExerciseName.YOGA -> "Yoga"
    }
}

fun getExerciseIcon(exercise:Exercise): Int {
    return when(exercise.name) {
        ExerciseName.HAND_GRIP -> R.drawable.hand_grip
        ExerciseName.JUMPING_ROPE -> R.drawable.jumping_rope
        ExerciseName.PUSH_UP -> R.drawable.push_up
        ExerciseName.SIT_UP -> R.drawable.sit_up
        ExerciseName.WEIGHTLIFTING -> R.drawable.weightlifting
        ExerciseName.YOGA -> R.drawable.yoga
    }
}






