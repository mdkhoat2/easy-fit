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
        "CRUNCHES",
        R.drawable.sit_up)

    data object PushUp : ExerciseItem(
        "PUSH UP",
        R.drawable.push_up
    )

    data object JumpingRope: ExerciseItem(
        "JUMPING ROPE",
        R.drawable.jumping_rope
    )

    data object WeightLifting: ExerciseItem(
        "WEIGHT LIFTING",
        R.drawable.weightlifting
    )

    data object Yoga: ExerciseItem(
        "YOGA",
        R.drawable.yoga
    )
}