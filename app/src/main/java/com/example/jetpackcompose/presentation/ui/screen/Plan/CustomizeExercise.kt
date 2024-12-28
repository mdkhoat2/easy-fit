package com.example.jetpackcompose.presentation.ui.screen.Plan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.jetpackcompose.data.dataModel.ExerciseType
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import com.example.jetpackcompose.presentation.ui.viewmodel.WorkoutViewModel

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun CustomizeExercise(
    navController: NavController,
    viewModel: WorkoutViewModel,
    exerciseIndex: Int
) {
    val uiState by viewModel.state.collectAsState()

    if (exerciseIndex >= uiState.queueExercise.size || uiState.queueExercise.isEmpty()) {
        return
    }

        val exercise = uiState.queueExercise[exerciseIndex].first
        val exerciseIcon = uiState.queueExercise[exerciseIndex].second

        var duration by rememberSaveable { mutableStateOf(exercise.duration) }
        var repetitions by rememberSaveable { mutableStateOf(exercise.repetition) }
        var restTime by rememberSaveable { mutableStateOf(exercise.restTime) }
        var isCountedMode by rememberSaveable { mutableStateOf(exercise.type == ExerciseType.COUNTED) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = colorFromResource(R.color.primary_teal)
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        viewModel.onExerciseRemoved(exerciseIndex)
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = colorFromResource(R.color.primary_teal)
                        )
                    }
                    IconButton(onClick = {
                        val updatedExercise = exercise.copy(
                            repetition = if (isCountedMode) repetitions else 0,
                            duration = if (!isCountedMode) duration else 0,
                            restTime = restTime,
                            type = if (isCountedMode) ExerciseType.COUNTED else ExerciseType.TIMED
                        )
                        viewModel.updateExercise(exerciseIndex, updatedExercise)
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save",
                            tint = colorFromResource(R.color.primary_teal)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Icon and Name
            Image(
                painter = painterResource(id = exerciseIcon),
                contentDescription = exercise.description,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .padding(32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))


            Spacer(modifier = Modifier.height(32.dp))

            // Mode Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 32.dp)
                    .background(Color.Transparent, CircleShape)
                    .border(
                        BorderStroke(2.dp, colorFromResource(R.color.primary_teal)),
                        CircleShape
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Counted Button
                Button(
                    onClick = { isCountedMode = true },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Transparent),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCountedMode) colorFromResource(R.color.primary_teal).copy(
                            alpha = 0.4f
                        ) else Color.Transparent,
                        contentColor = if (isCountedMode) Color.White else colorFromResource(R.color.primary_teal)
                    ),
                    elevation = null,
                    shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)
                ) {
                    Text(text = "Counted")
                }

                // Divider Line
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(colorFromResource(R.color.primary_teal))
                )

                // Timed Button
                Button(
                    onClick = { isCountedMode = false },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Transparent),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isCountedMode) colorFromResource(R.color.primary_teal).copy(
                            alpha = 0.4f
                        ) else Color.Transparent,
                        contentColor = if (!isCountedMode) Color.White else colorFromResource(R.color.primary_teal)
                    ),
                    elevation = null,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                ) {
                    Text(text = "Timed")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Repetition Slider
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isCountedMode) {
                    Text(text = "Repetitions", color = Color.Gray)
                    Slider(
                        value = repetitions.toFloat(),
                        onValueChange = { repetitions = it.toInt() },
                        valueRange = 1f..30f,
                        steps = 15,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Text(text = "$repetitions reps", color = Color.White)
                } else {
                    Text(text = "Duration", color = Color.Gray)
                    Slider(
                        value = duration.toFloat(),
                        onValueChange = { duration = it.toInt() },
                        valueRange = 0f..90f,
                        steps = 15,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Text(text = "$duration secs", color = Color.White)
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            // Rest Time Slider
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Rest time", color = Color.Gray)
                Slider(
                    value = restTime.toFloat(),
                    onValueChange = { restTime = it.toInt() },
                    valueRange = 5f..90f,
                    steps = 15,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(horizontal = 16.dp)
                )
                Text(text = "$restTime secs", color = Color.White)
            }
        }
}
