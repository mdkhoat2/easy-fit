package com.example.jetpackcompose.presentation.di


import com.example.jetpackcompose.R

sealed class ExerciseUIType{
    data class RepsBased(val totalReps: Int) : ExerciseUIType()
    data class TimeBased(val totalSeconds: Long) : ExerciseUIType()
}

sealed class ExerciseItem(
    val name: String,
    val id: Int,
    val description: String = "",
    val colorActive: Int = R.color.line_color,
    val colorInactive: Int = R.color.white,
    val type: ExerciseUIType
) {
    data object SitUp : ExerciseItem(
        "CRUNCHES",
        id = R.drawable.sit_up,
        type = ExerciseUIType.RepsBased(10)
    )

    data object PushUp : ExerciseItem(
        "PUSH UP",
        id = R.drawable.push_up,
        type = ExerciseUIType.RepsBased(10)
    )

    data object JumpingRope: ExerciseItem(
        "JUMPING ROPE",
        id = R.drawable.jumping_rope,
        type = ExerciseUIType.TimeBased(2)
    )

    data object WeightLifting: ExerciseItem(
        "WEIGHT LIFTING",
        id = R.drawable.weightlifting,
        type = ExerciseUIType.RepsBased(10)
    )

    data object Yoga: ExerciseItem(
        "YOGA",
        id = R.drawable.yoga,
        type = ExerciseUIType.TimeBased(60)
    )
}