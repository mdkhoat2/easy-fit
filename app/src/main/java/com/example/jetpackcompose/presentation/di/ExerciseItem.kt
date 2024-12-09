package com.example.jetpackcompose.presentation.di

import android.graphics.Color
import com.example.jetpackcompose.R

sealed class ExerciseItem(
    val name: String,
    val id: Int,
    val description: String = "",
    val colorActive: Int = R.color.line_color,
    val colorInactive: Int = R.color.white
) {
    data object SitUp : ExerciseItem(
        "Sit-up",
        R.drawable.sit_up)

    data object PushUp : ExerciseItem(
        "Push-up",
        R.drawable.push_up
    )

    data object JumpingRope: ExerciseItem(
        "Jumping Rope",
        R.drawable.jumping_rope
    )

    data object WeightLifting: ExerciseItem(
        "Weight Lifting",
        R.drawable.weightlifting
    )

    data object Yoga: ExerciseItem(
        "Yoga",
        R.drawable.yoga
    )
}