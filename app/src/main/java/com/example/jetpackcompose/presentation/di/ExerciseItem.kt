package com.example.jetpackcompose.presentation.di


import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.dataModel.Exercise
import com.example.jetpackcompose.data.dataModel.ExerciseType
import com.example.jetpackcompose.data.dataModel.getExerciseIcon
import com.example.jetpackcompose.data.dataModel.getExerciseString

sealed class ExerciseUIType{
    data class RepsBased(val totalReps: Int) : ExerciseUIType()
    data class TimeBased(val totalSeconds: Long) : ExerciseUIType()
}

data class ExerciseItem(
    val name: String,
    val id: Int,
    val description: String = "",
    val colorActive: Int = R.color.line_color,
    val colorInactive: Int = R.color.white,
    val type: ExerciseUIType
)

fun Exercise.toExerciseItem() : Pair<ExerciseItem,ExerciseItem> {
    val exerciseUIType: ExerciseUIType = if (this.type == ExerciseType.TIMED){
        ExerciseUIType.TimeBased(this.duration.toLong())
    }
    else ExerciseUIType.RepsBased(this.repetition)

    val restName = if (this.restTime==0) {
        "Delete"
    } else {
        "Rest"
    }

    return  Pair(
        ExerciseItem(
            name = getExerciseString(this),
            id = getExerciseIcon(this),
            description = this.description,
            type = exerciseUIType
        ),
        ExerciseItem(
            name = restName,
            id = R.drawable.heart_beat,
            description = "Take a break",
            type = ExerciseUIType.TimeBased(this.restTime.toLong())
        )
    )
}