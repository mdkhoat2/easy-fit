package com.example.jetpackcompose.presentation.ui.screen.Plan

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
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
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.CreateWorkoutUseCase
import com.example.jetpackcompose.presentation.ui.screen.Component.LineDivider
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import com.example.jetpackcompose.presentation.ui.viewmodel.NewWorkoutViewModel
import kotlinx.coroutines.launch

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun NewWorkoutScreen(
    navController: NavController,
    workoutDatabase: WorkoutDatabase,
    context: Context
) {
    val viewModel = remember {
        NewWorkoutViewModel(
            CreateWorkoutUseCase(
                WorkoutRepositoryImp(
                    context = context,
                    database = workoutDatabase
                )
            )
        )
    }

    val uiState = viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope() // Coroutine scope for composable

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
                text = "Create Workout",
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

        // Scrollable Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Makes the entire section scrollable
        ) {
            // Input Field for Workout Name
            Column {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Name",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                OutlinedTextField(
                    value = uiState.value.workoutName,
                    onValueChange = { viewModel.onWorkoutNameChanged(it) },
                    placeholder = { Text("Name your new workout here", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(), textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                    singleLine = true
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Maximum 20 letters",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Queue Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Queue",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = "Total: ${uiState.value.queueExercise.size}",
                    color = colorFromResource(R.color.primary_teal),
                    fontSize = 12.sp
                )
            }

            LineDivider()

            // Queue Items Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.heightIn(max = 1800.dp) // Restrict height to avoid infinite scroll issues
            ) {
                items(uiState.value.queueExercise.size) { index ->
                    val exercise = uiState.value.queueExercise[index]
                    QueueItem(
                        number = index + 1,
                        name = exercise.first.name.toString(),
                        iconId = exercise.second,
                        onRemoveClick = {
                            viewModel.onExerciseRemoved(index)
                        }
                    )
                }
                item {
                    if (uiState.value.queueExercise.isEmpty())
                        AddExerciseButton()
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



@Composable
fun QueueItem(
    number: Int,
    name: String,
    iconId: Int = R.drawable.push_up,
    onRemoveClick: () -> Unit // Callback for the minus icon click
) {
    Box(
        modifier = Modifier
            .size(100.dp) //
            .background(Color.Transparent, CircleShape)
    ) {
        // Centered exercise item
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExerciseItem(name = name, iconId = iconId)
        }
        // Top-left number
        Text(
            text = number.toString(),
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(4.dp)
        )

        // Top-right minus icon
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Remove Exercise",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(7.dp)
                .clickable { onRemoveClick() }
                .background(Color.DarkGray, CircleShape)
        )


    }
}


@Composable
fun AddExerciseButton() {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(Color.DarkGray, CircleShape)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "@", color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun ExerciseItem(
    name: String, iconId: Int= R.drawable.push_up,
    onClick: () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.DarkGray, CircleShape)
                .clickable {    onClick()                },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = ImageVector.vectorResource(iconId), contentDescription = name, tint = colorFromResource(R.color.primary_green))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, color = Color.White, fontSize = 11.sp)
    }
}


