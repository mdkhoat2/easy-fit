package com.example.jetpackcompose.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun colorFromResource(resource: Int): Color {
    val context = LocalContext.current
    val colorInt = ContextCompat.getColor(context, resource)
    return Color(colorInt)
}

@Composable
fun colorFromResourceAlpha(resource: Int, alpha: Float): Color {
    val context = LocalContext.current
    val colorInt = ContextCompat.getColor(context, resource)
    return Color(colorInt).copy(alpha = alpha)
}