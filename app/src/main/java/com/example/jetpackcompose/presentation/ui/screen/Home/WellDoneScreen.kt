package com.example.jetpackcompose.presentation.ui.screen.Home

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.AddDayToHistoryUseCase
import com.example.jetpackcompose.presentation.di.BottomBarScreen
import com.example.jetpackcompose.presentation.di.ExerciseUIType
import com.example.jetpackcompose.presentation.ui.uiState.SessionTrackingUIState
import com.example.jetpackcompose.ui.theme.AppTypo
import com.example.jetpackcompose.util.saveLastDate
import java.time.LocalDate

@Composable
fun WellDoneScreen(
    navController: NavController,
    state: SessionTrackingUIState,
    workoutDatabase: WorkoutDatabase,
    context: Context
) {
    val addDayToHistoryUseCase = AddDayToHistoryUseCase(
        WorkoutRepositoryImp(
            context = context,
            database = workoutDatabase
        )
    )

    // Add the day to the history
    LaunchedEffect(Unit) {
        addDayToHistoryUseCase(state.elapsedTime.toFloat())
        saveLastDate(context,LocalDate.now())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Well done!",
            style = AppTypo.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .border(BorderStroke(2.dp, colorResource(R.color.line_color)), shape = RoundedCornerShape(8.dp))
            ,
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.bottom_bar_background)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Exercises", color = Color.White, style = AppTypo.titleLarge)
                    // filter out the rest exercise
                    Text("${
                        state.exercises.filter { it.name != "Rest" }.size
                    }", color = Color(0xFFB4FF00), style = AppTypo.titleLarge)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Duration", color = Color.White, style = AppTypo.titleLarge)
                    Text(formatTime(state.elapsedTime), color = Color(0xFFB4FF00), style = AppTypo.titleLarge)
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))

                // Exercise list
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.exercises.size) { index ->
                        val exercise = state.exercises[index]
                        when (exercise.type) {
                            is ExerciseUIType.RepsBased -> {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(exercise.name, color = Color.White, style = AppTypo.titleMedium)
                                    Text("${exercise.type.totalReps}x", color = Color.White, style = AppTypo.titleMedium)
                                }
                            }
                            is ExerciseUIType.TimeBased -> {
                                if (exercise.name=="Rest" ) return@items
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(exercise.name, color = Color.White, style = AppTypo.titleMedium)
                                    Text("${exercise.type.totalSeconds} sec", color = Color.White, style = AppTypo.titleMedium)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = {
                    /* TODO: Handle save receipt button click */
                },
                modifier = Modifier
                    .width(162.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF9AC0D6),
                    containerColor = Color(0xFF9AC0D6).copy(alpha = 0.2f)
                )
            ){
                Text(
                    text = "Save Receipt",
                    style = AppTypo.titleSmall,
                    color = Color(0xFF9AC0D6)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.save),
                    contentDescription = "Arrow",
                    tint = Color(0xFF9AC0D6),
                    modifier = Modifier.size(24.dp)
                )
            }
            OutlinedButton(
                onClick = { // put extra data in the bundle
                    navController.navigate(BottomBarScreen.Home.route)
                },
                modifier = Modifier
                    .width(162.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF9AC0D6),
                    containerColor = Color(0xFF9AC0D6).copy(alpha = 0.2f)
                )
            ){
                Text(
                    text = "Return Home",
                    style = AppTypo.titleSmall,
                    color = Color(0xFF9AC0D6)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow",
                    tint = Color(0xFF9AC0D6)
                )
            }
        }
    }
}


//@Preview
//@Composable
//fun WellDoneScreenPreview() {
//    val state = SessionTrackingUIState()
//    WellDoneScreen(
//        navController = NavController(LocalContext.current),
//        state = state
//    )
//}
