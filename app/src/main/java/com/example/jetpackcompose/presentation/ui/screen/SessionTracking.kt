package com.example.jetpackcompose.presentation.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.getValue
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
import com.example.jetpackcompose.ui.theme.Typography

@Composable
fun SessionTrackingScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(navController)
        Spacer(modifier = Modifier.weight(1f))
        CurrentExercise()
    }
}

@Composable
fun TopBar(navController: NavController){
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
}

@Composable
fun CurrentExercise(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "10x reps",
            color = Color.White,
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedCircularFillWithButton()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CRUNCHES",
            color = Color.White, 
            style = Typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Take it slowly and feel of your core",
            color = Color.White,
            style = Typography.bodySmall
        )

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun AnimatedCircularFillWithButton() {
    var isAnimating by remember { mutableStateOf(false) } // State to trigger the animation
    val animatedSweepAngle = remember { Animatable(0f) }

    // LaunchedEffect to handle the animation
    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            animatedSweepAngle.snapTo(0f) // Reset the animation
            animatedSweepAngle.animateTo(
                targetValue = 360f, // Full circle
                animationSpec = tween(
                    durationMillis = 15000,
                    easing = LinearEasing
                )
            )
            isAnimating = false // Reset the state after animation completes
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
        Button(
            onClick = { isAnimating = true }, // Trigger animation on click
            shape = CircleShape,
            modifier = Modifier.size(150.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.home_btn),
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sit_up),
                contentDescription = "Sit-up",
                tint = colorResource(R.color.line_color),
                modifier = Modifier.size(85.dp)
            )
        }
    }
}


@Composable
@Preview
fun SessionTrackingPreview(){
    SessionTrackingScreen(navController = NavController(LocalContext.current))
}
