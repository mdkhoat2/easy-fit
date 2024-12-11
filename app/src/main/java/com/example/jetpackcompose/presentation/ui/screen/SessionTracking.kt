package com.example.jetpackcompose.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.example.jetpackcompose.presentation.di.ExerciseUIType
import com.example.jetpackcompose.presentation.ui.viewmodel.SessionTrackingViewModel
import com.example.jetpackcompose.ui.theme.Typography
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun SessionTrackingScreen(navController: NavController, viewModel: SessionTrackingViewModel){
    val uiState by viewModel.state.collectAsState()
    var showExitDialog by remember { mutableStateOf(false)}

    BackHandler {
        showExitDialog = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        WorkoutTopBar(
            time = uiState.elapsedTime,
            isPaused = uiState.isPaused,
            onPauseClick = { viewModel.togglePause() },
            onCloseClick = { showExitDialog = true }
        )

        Spacer(modifier = Modifier.weight(1f))

        uiState.currentExercise?.let { exercise ->
            CurrentExercise(
                isPaused = uiState.isPaused,
                exerciseItem = exercise,
                onCycleComplete = { viewModel.nextExercise() }
            )
        } ?: Text("No exercise available", color = Color.White)

        Spacer(modifier = Modifier.weight(1f))

        // Bottom row
        BottomListExercises(uiState.exercises, uiState.currentExerciseIndex)

        Spacer(modifier = Modifier.height(32.dp))
    }

    if (showExitDialog) {
        ExitWorkoutDialog(
            onConfirm = {
                navController.navigateUp()
            },
            onDismiss = {
                showExitDialog = false
            }
        )
    }
}

@Composable
fun WorkoutTopBar(
    time: Long,
    isPaused: Boolean,
    onPauseClick: () -> Unit,
    onCloseClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = onCloseClick) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color(0xFF9AC0D6)
            )
        }

        Text(
            text = formatTime(time),
            color = Color.White,
            style = Typography.bodyLarge
        )

        IconButton(onClick = onPauseClick) {
            Icon(
                imageVector = if (isPaused) {
                    Icons.Default.PlayArrow
                } else {
                    ImageVector.vectorResource(id = R.drawable.icon_pause)
                },
                contentDescription = if (isPaused) "Resume" else "Pause",
                tint = Color(0xFF9AC0D6)
            )
        }
    }
}

@Composable
private fun ExitWorkoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Exit Workout?") },
        text = { Text("Are you sure you want to end this workout? Your progress will not be saved.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Exit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Continue")
            }
        }
    )
}

@SuppressLint("DefaultLocale")
private fun formatTime(timeInMillis: Long): String {
    val seconds = (timeInMillis / 1000) % 60
    val minutes = (timeInMillis / (1000 * 60)) % 60
    return String.format("%02d:%02d", minutes, seconds)
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
        when (exerciseItem.type) {
            is ExerciseUIType.RepsBased -> {
                Text(
                    text = "${exerciseItem.type.totalReps}x reps",
                    color = Color.White,
                    style = Typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                RepBasedExercise(
                    exerciseItem = exerciseItem,
                    onComplete = onCycleComplete
                )
            }
            is ExerciseUIType.TimeBased -> {
                var remainingSeconds by remember { mutableLongStateOf(exerciseItem.type.totalSeconds) }
                Text(
                    text = formatTime(remainingSeconds),
                    color = Color.White,
                    style = Typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                TimeBasedExercise(
                    isPaused = isPaused,
                    exerciseItem = exerciseItem,
                    totalSeconds = exerciseItem.type.totalSeconds,
                    onTimeUpdate = {remainingSeconds = it},
                    onComplete = onCycleComplete
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = exerciseItem.name,
            color = Color.White,
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = exerciseItem.description,
            color = Color.White,
            style = Typography.bodyMedium
        )
    }
}

@Composable
fun TimeBasedExercise(
    isPaused: Boolean,
    exerciseItem: ExerciseItem,
    totalSeconds: Long,
    onTimeUpdate: (Long) -> Unit,
    onComplete: () -> Unit
) {
    val animatedSweepAngle = remember { Animatable(0f) }
    var lastValue by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isPaused){
        if (!isPaused){
            try {
                animatedSweepAngle.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(
                        durationMillis = (totalSeconds * 1000 * (1 - lastValue / 360f)).toInt(),
                        easing = LinearEasing
                    )
                ){
                    val progress = this.value / 360f
                    val remainingTime = ((1 - progress) * totalSeconds).toLong()
                    onTimeUpdate(remainingTime)
                }
                onComplete()
                lastValue = 0f
                animatedSweepAngle.snapTo(0f)
            }
            catch (e : CancellationException){
                lastValue = animatedSweepAngle.value
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(180.dp)
    ){
        Canvas(modifier = Modifier.size(170.dp)){
            drawArc(
                color = Color(0xFFD5FF5F),
                startAngle = -90f,
                sweepAngle = animatedSweepAngle.value,
                useCenter = true,
                style = Fill
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .background(color = colorResource(R.color.home_btn), shape = CircleShape)
        ){
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
fun RepBasedExercise(exerciseItem: ExerciseItem, onComplete: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(180.dp)
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .background(color = colorResource(R.color.home_btn), shape = CircleShape)
                .clickable { onComplete() }
        ){
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
    currentExerciseIndex: Int
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
