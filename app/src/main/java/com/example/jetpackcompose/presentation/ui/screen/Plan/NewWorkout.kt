package com.example.jetpackcompose.presentation.ui.screen.Plan

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun NewWorkoutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Back",
                color = Color.Blue,
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Text(
                text = "New Workout",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Save",
                color = Color.Blue,
                fontSize = 16.sp,
                modifier = Modifier.clickable { /* Save Action */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Field for Workout Name
        Column {
            Text("Name", color = Color.Gray, fontSize = 14.sp)
            OutlinedTextField(
                value = "",
                onValueChange = { /* Handle input */ },
                placeholder = { Text("Name your new workout here", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,

            )
            Text(
                text = "Maximum 15 letters",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Queue Section
        Text(
            text = "Queue",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            QueueItem(number = 1, name = "PUSH UP")
            QueueItem(number = 2, name = "CRUNGES")
            AddExerciseButton()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise Section
        Text(
            text = "Exercise",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Exercise Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        }
    }
}

@Composable
fun QueueItem(number: Int, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color.DarkGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = number.toString(), color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun AddExerciseButton() {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(Color.DarkGray, CircleShape)
            .clickable { /* Handle Add Action */ },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "+", color = Color.White, fontSize = 24.sp)
    }
}

@Composable
fun ExerciseItem(name: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color.DarkGray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = name, tint = Color(0xFFADFF2F))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, color = Color.White, fontSize = 10.sp)
    }
}

// Sample Exercise Data
val exerciseList = listOf(
    Exercise("BREATHE", Icons.Default.Favorite),
    Exercise("PUSH UP", Icons.Default.Favorite),
    Exercise("WEIGHT LIFT", Icons.Default.Favorite),
    Exercise("CRUNGES", Icons.Default.Favorite),
    Exercise("ROPE JUMP", Icons.Default.Favorite),
    Exercise("HAND GRIP", Icons.Default.Favorite),
    Exercise("HOOP", Icons.Default.Favorite),
    Exercise("ROPE JUMP", Icons.Default.Favorite),
)

data class Exercise(val name: String, val icon: ImageVector)
