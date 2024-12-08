package com.example.jetpackcompose.data.dataModel

enum class ExerciseName {
    PUSH_UP,
    SIT_UP,
    PLANK,
    LEG_RAISE,
    CRUNCH,
    MOUNTAIN_CLIMBER,
    JUMPING_JACK,
    HIGH_KNEES,
    BURPEE
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
    val restTime: Int = 15 // Rest time between sets in seconds
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




