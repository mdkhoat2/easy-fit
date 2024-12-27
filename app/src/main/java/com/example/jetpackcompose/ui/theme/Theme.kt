package com.example.jetpackcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val FixedDarkColorScheme = darkColorScheme(
    primary = Green,            // For primary buttons or active elements
    secondary = Blue,           // For secondary buttons or highlights
    onPrimary = Black,          // Text/icons on primary color
    background = Black,         // App background
    surface = DarkGrey,         // Cards or other surfaces
    onBackground = White,       // Text/icons on the background
    onSurface = White,          // Text/icons on surfaces
    error = Orange,             // For error states or missed goals
    onError = Black             // Text/icons on error states
)

@Composable
fun JetPackComposeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FixedDarkColorScheme,
        typography = Typography,
        content = content
    )
}