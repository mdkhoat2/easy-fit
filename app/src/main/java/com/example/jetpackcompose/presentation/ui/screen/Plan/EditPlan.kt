package com.example.jetpackcompose.presentation.ui.screen.Plan

import android.app.TimePickerDialog
import android.content.Context
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
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.dataModel.DayOfWeek
import com.example.jetpackcompose.data.dataModel.Plan
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetPlanUseCase
import com.example.jetpackcompose.domain.usecase.UpdatePlanUseCase
import com.example.jetpackcompose.presentation.ui.screen.Component.LineDivider
import com.example.jetpackcompose.presentation.ui.screen.colorFromResource
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Created by Duy on 29/11/2024
 * @project EasyFit
 * @author Duy
 */

@Composable
fun EditPlan(
    navController: NavController,
    workoutDatabase: WorkoutDatabase,
    context: Context,
) {
    val repository = remember { WorkoutRepositoryImp( workoutDatabase,context) }
    val getPlanUseCase = remember { GetPlanUseCase(repository) }
    val updatePlanUseCase = remember { UpdatePlanUseCase(repository) }

    // Plan State
    var plan by remember { mutableStateOf<Plan?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Fetch Plan
    LaunchedEffect(Unit) {
        plan = getPlanUseCase()
    }
    // UI Loading State
    if (plan == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
        return
    }

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
            IconButton(onClick = {
                coroutineScope.launch {
                    updatePlanUseCase(plan!!)
                    navController.popBackStack()
                }
            }) {
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
        DayOfWeek.entries.forEachIndexed { index, day ->
            val isChecked = plan!!.dateWorkout.contains(day) ?: false
            val time = plan!!.timeWorkout.getOrNull(index) ?: "No plan"

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Day Name
                Text(
                    text = day.name,
                    color = if (isChecked) colorFromResource(R.color.primary_green) else Color.Gray,
                    fontSize = 16.sp
                )

                // Time or "No plan"
                Row(
                    modifier = Modifier
                        .background(Color.Black)
                        .padding(horizontal = 16.dp)
                ) {
                    if (isChecked) {
                        Text(
                            text = time,
                            color = Color(0xFF9AC0D6),
                            fontSize = 16.sp
                        )
                    } else {
                        Text(
                            text = "No plan",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }

                    // Checkbox
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { checked ->
                            if (checked) {
                                // Show Time Picker Dialog
                                showTimePicker(context, time) { selectedTime ->
                                    val updatedDates = plan!!.dateWorkout.toMutableList().apply { add(day) }
                                    val updatedTimes = plan!!.timeWorkout.toMutableList().apply {
                                        if (index < size) set(index, selectedTime) else add(selectedTime)
                                    }
                                    plan = plan!!.copy(dateWorkout = updatedDates, timeWorkout = updatedTimes)
                                }
                            } else {
                                // Remove Day and Reset Time
                                val updatedDates = plan!!.dateWorkout.toMutableList().apply { remove(day) }
                                val updatedTimes = plan!!.timeWorkout.toMutableList().apply {
                                    if (index < size) set(index, "No plan")
                                }
                                plan = plan!!.copy(dateWorkout = updatedDates, timeWorkout = updatedTimes)
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
        }
        LineDivider()
    }
}

fun showTimePicker(
    context: Context,
    currentTime: String,
    onTimeSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    // Parse current time if available
    val (currentHour, currentMinute) = try {
        currentTime.split(":").map { it.toInt() }.let {
            it[0] to it[1]
        }
    } catch (e: Exception) {
        Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }


    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeSelected(formattedTime)
        },
        currentHour,
        currentMinute,
        true // Use 24-hour format
    ).show()
}


//@Preview(showBackground = true)
//@Composable
//fun EditPlanScreenPreview() {
//    EditPlan(
//        navController = rememberNavController(),
//        onSave = { updatedPlan ->
//            Log.d("EditPlanScreen", "Saved plan: $updatedPlan")
//        }
//    )
//}
