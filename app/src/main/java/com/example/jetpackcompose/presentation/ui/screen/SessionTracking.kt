package com.example.jetpackcompose.presentation.ui.screen

import android.graphics.drawable.Icon
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcompose.R
import com.example.jetpackcompose.presentation.di.ExerciseItem
import com.example.jetpackcompose.presentation.ui.viewmodel.SessionTrackingViewModel
import com.example.jetpackcompose.ui.theme.Typography
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun SessionTrackingScreen(navController: NavController, viewModel: SessionTrackingViewModel){
    val uiState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // State to track if the workout is paused
        var isPaused by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = {navController.navigateUp()}){
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color(0xFF9AC0D6)
                )
            }
            Text(
                text = "00:36",
                color = Color.White,
                style = Typography.bodyLarge
            )
            IconButton(onClick = {isPaused = !isPaused}){
                Icon(
                    imageVector = if (isPaused) Icons.Default.PlayArrow else ImageVector.vectorResource(id = R.drawable.icon_pause),
                    contentDescription = "Pause",
                    tint = Color(0xFF9AC0D6)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CurrentExercise(isPaused, uiState.currentExercise!!, onCycleComplete = {
            viewModel.nextExercise()
        })

        Spacer(modifier = Modifier.weight(1f))

        // Bottom row
        BottomListExercises(uiState.exercises)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CurrentExercise(
    isPaused: Boolean,
    exerciseItem: ExerciseItem,
    onCycleComplete: () -> Unit = {}
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "10x reps",
            color = Color.White,
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedCircularFillWithButton(isPaused, exerciseItem, onCycleComplete)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = exerciseItem.name,
            color = Color.White, 
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Take it slowly and feel of your core",
            color = Color.White,
            style = Typography.bodySmall
        )
    }
}

@Composable
fun AnimatedCircularFillWithButton(
    isPaused: Boolean,
    exerciseItem: ExerciseItem,
    onCycleComplete: () -> Unit
) {
    val animatedSweepAngle = remember { Animatable(0f) }
    var lastValue by remember { mutableFloatStateOf(0f) }

    // LaunchedEffect to handle the animation
    LaunchedEffect(isPaused) {
        while (!isPaused) {
            try {
                // Start animation from last paused position
                animatedSweepAngle.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(
                        durationMillis = (15000 * (1 - lastValue / 360f)).toInt(), // Adjust duration based on progress
                        easing = LinearEasing
                    )
                )
                onCycleComplete()
                lastValue = 0f
                animatedSweepAngle.snapTo(0f) // Reset the animation for the next cycle
            } catch (e: CancellationException) {
                // Store the last value when cancelled
                lastValue = animatedSweepAngle.value
                break
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(180.dp)
    ) {
        // Canvas with animated arc
        Canvas(modifier = Modifier.size(170.dp)) {
            drawArc(
                color = Color(0xFFD5FF5F),
                startAngle = -90f, // Start at the top
                sweepAngle = animatedSweepAngle.value, // Animated sweep angle
                useCenter = true,
                style = Fill,
            )
        }

        // Button in the center
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .background(color = colorResource(R.color.home_btn), shape = CircleShape)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = exerciseItem.id),
                contentDescription = exerciseItem.description,
                tint = colorResource(exerciseItem.colorActive),
                modifier = Modifier.size(85.dp)
            )
        }
    }
}

@Composable
fun BottomListExercises(
    exercises: List<ExerciseItem>,
    currentExerciseIndex: Int = 0
){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ){
        items(exercises.size){
            index ->
            val exercise = exercises[index]
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .background(color = colorResource(R.color.home_btn), shape = CircleShape)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = exercise.id),
                    contentDescription = exercise.description,
                    tint = if (index == currentExerciseIndex) colorResource(exercise.colorActive) else colorResource(exercise.colorInactive),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Composable
@Preview
fun SessionTrackingPreview(){
    SessionTrackingScreen(navController = NavController(LocalContext.current), viewModel = SessionTrackingViewModel())
}
