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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExercise(
    navController: NavController,
    workoutDatabase: WorkoutDatabase,
    context: Context,
) {
    val repository = remember { WorkoutRepositoryImp(workoutDatabase, context) }
    val updatePlanUseCase = remember { UpdatePlanUseCase(repository) }
    val coroutineScope = rememberCoroutineScope()

    // States for name and description
    var exerciseName by remember { mutableStateOf("") }
    var exerciseDescription by remember { mutableStateOf("") }

    // Validation or UI Loading State
    val isSaveEnabled = exerciseName.isNotBlank() && exerciseName.length <= 15

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
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
                text = "New Action",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        // Save the new action (if connected to DB or logic here)
                        navController.popBackStack()
                    }
                },
                enabled = isSaveEnabled
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save",
                    tint = if (isSaveEnabled) Color.White else Color.Gray
                )
            }
        }

        // Description
        Text(
            text = "An action is half of an exercise. The other half is time/reps configured when you queue an exercise to your workout.",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Icon Placeholder
        Image(
            painter = painterResource(id = R.drawable.custom_exercise),
            contentDescription = "Exercise Icon",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.DarkGray)
                .padding(32.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Name Input
        OutlinedTextField(
            value = exerciseName,
            onValueChange = {
                if (it.length <= 15) exerciseName = it
            },
            label = { Text(text = "Name*", color = Color.Gray) },
            placeholder = { Text(text = "Name your new action here", color = Color.Gray) },
            maxLines = 1,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )
        Text(
            text = "Maximum 15 letters",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.Start).padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description Input
        OutlinedTextField(
            value = exerciseDescription,
            onValueChange = { exerciseDescription = it },
            label = { Text(text = "Description", color = Color.Gray) },
            placeholder = { Text(text = "Briefly describe the action", color = Color.Gray) },
            maxLines = 3,
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )
    }
}
