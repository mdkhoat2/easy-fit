package com.example.jetpackcompose.presentation.ui.screen.Plan

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.domain.usecase.GetWorkoutByIdUseCase
import com.example.jetpackcompose.presentation.di.Routes
import com.example.jetpackcompose.presentation.ui.screen.Component.LineDivider
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import com.example.jetpackcompose.presentation.ui.viewmodel.EditWorkoutViewModel
import com.example.jetpackcompose.presentation.ui.viewmodel.NewWorkoutViewModel
import kotlinx.coroutines.launch

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun EditWorkoutScreen(
    navController: NavController,
    viewModel: EditWorkoutViewModel,
    workoutId: String,
) {
    val uiState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadWorkout(workoutId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Back",
                color = colorFromResource(R.color.primary_teal),
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.popBackStack() }
            )

            Text(
                text = "Edit Workout",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Save",
                color = colorFromResource(R.color.primary_teal),
                fontSize = 16.sp,
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        viewModel.onSavePressed()
                        navController.popBackStack()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Workout Details
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            // Workout Name
            Text("Name", color = Color.Gray, fontSize = 14.sp)
            OutlinedTextField(
                value = uiState.workoutName,
                onValueChange = viewModel::onWorkoutNameChanged,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                singleLine = true,
                placeholder = { Text("Edit workout name", color = Color.Gray) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Queue Section
            Text("Queue", color = Color.White, fontSize = 16.sp)
            LineDivider()

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.heightIn(max = 1800.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.queueExercise.size) { index ->
                    val exercise = uiState.queueExercise[index]
                    QueueItem(
                        number = index + 1,
                        name = exercise.first.name.toString(),
                        iconId = exercise.second,
                        onRemoveClick = { viewModel.onExerciseRemoved(index) },
                        onClick = { navController.navigate("${Routes.CustomizeExercise}/$index") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Exercise Section
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = "Exercise",
                color = Color.White,
                fontSize = 16.sp
            )
            LineDivider()

            // Exercise Items Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.heightIn(max = 300.dp)
            ) {
                items(viewModel.state.value.availableExercises.size) { index ->
                    val exercise = viewModel.state.value.availableExercises[index]
                    ExerciseItem(
                        name = exercise.first.name.toString(),
                        iconId = exercise.second,
                        onClick = {
                            viewModel.onExerciseSelected(exercise.first.name.toString())
                        }
                    )
                }
            }
        }
    }
}
