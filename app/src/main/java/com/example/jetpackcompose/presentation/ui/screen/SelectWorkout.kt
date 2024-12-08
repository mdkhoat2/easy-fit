package com.example.jetpackcompose.presentation.ui.screen

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.dataModel.Workout
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectWorkoutsScreen(getWorkouts: suspend () -> List<Workout>) {
    var workouts by remember { mutableStateOf<List<Workout>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            workouts = getWorkouts()
        }
    }

    Scaffold(
        contentColor = Color.Gray,
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Select workouts") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back button */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Workout items
                items(workouts) { workout ->
                    WorkoutItem(workoutName = workout.name,
                        onClick = {
                            println("Item printe")
                        })
                }

                // Add the search bar as the last item
                item {
                    OutlinedTextField(
                        value = "",
                        onValueChange = { /* Handle search input */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                            .background(colorFromResource(R.color.bottom_bar_background))
                        ,
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        },
                        placeholder = { Text("Search library") }
                    )
                }
            }
        }
    }
}




@Composable
fun WorkoutList(workouts: List<String>, onItemClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(workouts) { workout ->
            WorkoutItem(workoutName = workout, onClick = { onItemClick(workout) })
        }
    }
}

@Composable
fun WorkoutItem(workoutName: String,onClick: () -> Unit ) { //set onClickListener to the item
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
                onClick =
                {
                    /* Handle edit button click */

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.size(24.dp),
                    tint = colorFromResource(R.color.btn_back_color)
                )
            }

        }
    }

}

// preview
@Preview
@Composable
fun SelectWorkoutsScreenPreview() {
    //make a sample list of workouts
    val workouts = listOf(
        Workout("1", null, "Crunch", emptyList(), 30),
        Workout("2", null, "Push Up", emptyList(), 45),
        Workout("3", null, "Quay Tay", emptyList(), 60)
    )
    SelectWorkoutsScreen(getWorkouts = { workouts })
}