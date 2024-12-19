package com.example.jetpackcompose.data.dataModel

import com.example.jetpackcompose.R
import org.hamcrest.Description

enum class ExerciseName {
    HAND_GRIP,
    JUMPING_ROPE,
    PUSH_UP,
    SIT_UP,
    WEIGHTLIFTING,
    YOGA,
    BREAK
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
    val description: String
)

fun getExerciseString(exercise:Exercise): String {
    return when(exercise.name) {
        ExerciseName.HAND_GRIP -> "Hand Grip"
        ExerciseName.JUMPING_ROPE -> "Jumping Rope"
        ExerciseName.PUSH_UP -> "Push Up"
        ExerciseName.SIT_UP -> "Sit Up"
        ExerciseName.WEIGHTLIFTING -> "Weightlifting"
        ExerciseName.YOGA -> "Yoga"
        ExerciseName.BREAK -> "Break"
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
        ExerciseName.BREAK -> R.drawable.heart_beat
    }
}

fun getAllExercises(): List<Exercise> {
    return listOf(
        Exercise(
            name = ExerciseName.HAND_GRIP,
            description = "Hand Grip is a simple exercise that can help you build strength in your hands and forearms."
        ),
        Exercise(
            name = ExerciseName.JUMPING_ROPE,
            description = "Jumping Rope is a great cardio exercise that can help you burn calories and improve your heart health."
        ),
        Exercise(
            name = ExerciseName.PUSH_UP,
            description = "Push Up is a classic exercise that can help you build strength in your chest, shoulders, and triceps."
        ),
        Exercise(
            name = ExerciseName.SIT_UP,
            description = "Sit Up is a core exercise that can help you build strength in your abdominal muscles."
        ),
        Exercise(
            name = ExerciseName.WEIGHTLIFTING,
            description = "Weightlifting is a strength training exercise that can help you build muscle mass and improve your overall strength."
        ),
        Exercise(
            name = ExerciseName.YOGA,
            description = "Yoga is a mind-body exercise that can help you improve your flexibility, strength, and mental well-being."
        )
    )
}

fun generateWorkoutID(): String {
    return "workout_${System.currentTimeMillis()}"
}