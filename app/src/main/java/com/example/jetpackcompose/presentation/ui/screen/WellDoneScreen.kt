package com.example.jetpackcompose.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WellDoneScreen(
    streak: Int,
    exercises: String,
    duration: String,
    exerciseDetails: List<Pair<String, String>>,
    onSaveReceiptClick: () -> Unit,
    onReturnHomeClick: () -> Unit
) {
    Scaffold(
        contentColor = Color.Gray,
        containerColor = Color.Black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // "Well Done!" Text
            Text(
                text = "Well done!",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Stats Card
            Box(
                modifier = Modifier
                    .background(Color(0xFF00FF00).copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Stats
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Streak", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text("$streak days", color = Color.Green, style = MaterialTheme.typography.bodyMedium)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Exercises", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text("$exercises", color = Color.Green, style = MaterialTheme.typography.bodyMedium)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Duration", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        Text(duration, color = Color.Green, style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Separator Line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray.copy(alpha = 0.5f))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Exercise Details
                    exerciseDetails.forEach { (exercise, detail) ->
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text(exercise, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                            Text(detail, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onSaveReceiptClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                ) {
                    Text("Save Receipt")
                }
                Button(
                    onClick = onReturnHomeClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                ) {
                    Text("Return Home")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WellDoneScreenPreview() {
    val exerciseDetails = listOf(
        "Crunches" to "10×",
        "Lunges" to "12×",
        "Squats" to "20×",
        "Kicks" to "20×",
        "Wall sit" to "30 sec"
    )
    WellDoneScreen(
        streak = 40,
        exercises = "Legs and Core",
        duration = "6m4s",
        exerciseDetails = exerciseDetails,
        onSaveReceiptClick = { /* Handle Save Receipt */ },
        onReturnHomeClick = { /* Handle Return Home */ }
    )
}
