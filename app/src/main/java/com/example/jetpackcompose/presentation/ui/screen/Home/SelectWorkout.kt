package com.example.jetpackcompose.presentation.ui.screen.Home

import android.content.Context
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import com.example.jetpackcompose.presentation.di.Routes
import com.example.jetpackcompose.presentation.ui.screen.Component.BigButtonWithIcon
import com.example.jetpackcompose.presentation.ui.screen.Component.ButtonAlign
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import com.example.jetpackcompose.presentation.ui.viewmodel.SelectWorkoutViewModel
import com.example.jetpackcompose.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectWorkoutsScreen(
    navController: NavController,
    workoutDatabase: WorkoutDatabase,
    context: Context
) {
    val selectWorkoutViewModel = remember {
        val getYourWorkoutsUseCase = GetYourWorkoutsUseCase(
            WorkoutRepositoryImp(
                context = context,
                database = workoutDatabase
            )
        )
        SelectWorkoutViewModel(getYourWorkoutsUseCase)
    }

    val state by selectWorkoutViewModel.state.collectAsState()
    val workouts = state.filteredWorkouts.takeIf { state.searchQuery.isNotBlank() } ?: state.workouts

    val searchQuery = remember { mutableStateOf(state.searchQuery) }

    Scaffold(
        contentColor = Color.Gray,
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Select workouts") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorFromResource(R.color.bottom_bar_background),
                    titleContentColor = Color.White,
                    navigationIconContentColor = colorFromResource(R.color.btn_back_color)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Workout items
                items(workouts) { workout ->
                    WorkoutItem(
                        workoutName = workout.name,
                        onClick = {
                            navController.navigate("${Routes.sessionTracking}/${workout.id}")
                        },
                        onEditClick = {
                            navController.navigate("${Routes.EditWorkout}/${workout.id}")
                        }
                    )
                }

                // Search Button
                item {
                    BigButtonWithIcon(
                        onClick = {
                            navController.navigate(Routes.selectWorkout)
                        },
                        icon = Icons.Default.Search,
                        text = "Search",
                        align = ButtonAlign.START
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutItem(workoutName: String,onClick: () -> Unit,onEditClick: () -> Unit) { //set onClickListener to the item
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(colorFromResource(R.color.bottom_bar_background))
            .clickable {  onClick() }
            .padding(vertical = 4.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = workoutName,
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Vertical line
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(24.dp) // Adjust height to align with the icon
                    .background(Color.Gray) // Set the color of the line
            )

            Spacer(modifier = Modifier.width(8.dp)) // Space between line and icon

            IconButton(
                onClick ={onEditClick()}
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF9AC0D6)
                )
            }

        }
    }

}

@Composable
fun SearchButton(navController: NavController){
    Box( // Replace Column with Box to control the size
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(8))
            .background(colorFromResource(R.color.bottom_bar_background)),
        contentAlignment = Alignment.Center
    ){
        OutlinedButton(
            onClick = {
                navController.navigate(Routes.selectWorkout)
            },
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorFromResource(R.color.primary_teal),
                containerColor = colorFromResource(R.color.primary_teal).copy(alpha = 0.1f)
            ),shape = RoundedCornerShape(8)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Save",
                    tint = colorFromResource(R.color.primary_teal)

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search",
                    style = Typography.bodyLarge,
                    color = colorFromResource(R.color.primary_teal)
                )
            }
        }
    }
}

// preview
@Preview
@Composable
fun SaveButton() {

}

