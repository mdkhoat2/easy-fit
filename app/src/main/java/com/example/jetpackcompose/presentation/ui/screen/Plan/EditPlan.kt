package com.example.jetpackcompose.presentation.ui.screen.Plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import com.example.jetpackcompose.presentation.ui.screen.Component.LineDivider

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun EditPlan(
    navController: NavController,
    initialPlan: Map<String, Pair<String, Boolean>> = mapOf(
        "Monday" to Pair("20:00", true),
        "Tuesday" to Pair("No plan", false),
        "Wednesday" to Pair("20:00", true),
        "Thursday" to Pair("No plan", false),
        "Friday" to Pair("20:00", true),
        "Saturday" to Pair("No plan", false),
        "Sunday" to Pair("20:00", true)
    ),
    onSave: (Map<String, Pair<String, Boolean>>) -> Unit
) {
    var plan by remember { mutableStateOf(initialPlan) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Edit Plan",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { onSave(plan) }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save",
                    tint = Color.White
                )
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Description Text
        Text(
            text = "Tick days in a week that you commit. For each day, a time picker will appear and let you choose when the notification will be sent.",
            color = Color.Gray,
            fontSize = 14.sp
        )


        Spacer(modifier = Modifier.height(16.dp))
        LineDivider()

        // Days List
        plan.forEach { (day, value) ->
            val (time, isChecked) = value

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Day Name
                Text(
                    text = day,
                    color = if (isChecked) Color(0xFFFFD700) else Color.Gray,
                    fontSize = 16.sp
                )

                // Time or "No plan"
                if (isChecked) {
                    TextButton(
                        onClick = {

                        }
                    ) {
                        Text(
                            text = time,
                            color = Color(0xFF9AC0D6),
                            fontSize = 14.sp
                        )
                    }
                } else {
                    Text(
                        text = "No plan",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }

                // Checkbox
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        plan = plan.toMutableMap().apply {
                            this[day] = Pair(if (checked) "20:00" else "No plan", checked)
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.White,
                        uncheckedColor = Color.Gray,
                        checkedColor = Color(0xFF9AC0D6)
                    )
                )
            }
        }
        LineDivider()
    }
}

@Composable
fun openTimePicker(currentTime: String): String {
    // This function should show a time picker dialog and return the selected time.
    // Replace with actual time picker logic.
    return currentTime // Placeholder
}

@Preview(showBackground = true)
@Composable
fun EditPlanScreenPreview() {
    EditPlan(
        navController = rememberNavController(),
        onSave = { updatedPlan ->
            Log.d("EditPlanScreen", "Saved plan: $updatedPlan")
        }
    )
}
