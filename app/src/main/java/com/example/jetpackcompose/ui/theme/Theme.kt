package com.example.jetpackcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FixedDarkColorScheme = darkColorScheme(
    primary = Colors.Green,            // For primary buttons or active elements
    secondary = Colors.Blue,           // For secondary buttons or highlights
    onPrimary = Colors.Black,          // Text/icons on primary color
    background = Colors.Black,         // App background
    surface = Colors.DarkGrey,         // Cards or other surfaces
    onBackground = Colors.White,       // Text/icons on the background
    onSurface = Colors.White,          // Text/icons on surfaces
    error = Colors.Orange,             // For error states or missed goals
    onError = Colors.Black             // Text/icons on error states
)

@Composable
fun JetPackComposeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FixedDarkColorScheme,
        typography = AppTypo,
        content = content
    )
}