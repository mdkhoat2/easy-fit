package com.example.jetpackcompose.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
        IconButton(onClick = {/* Handle back button click */}){
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
@Preview
fun SelectWorkoutPreview(){
    TopBar(navController = NavController(LocalContext.current))
}
