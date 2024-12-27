package com.example.jetpackcompose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import com.example.jetpackcompose.R

// Define JakartaSans FontFamily with specific weights
val JakartaSans = FontFamily(
    Font(R.font.jakarta, FontWeight.Normal), // Regular
    Font(R.font.jakarta, FontWeight.Medium)  // Medium
)

// Set of Material typography styles
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = JakartaSans,         // Use JakartaSans
        fontWeight = FontWeight.Normal,   // Regular weight
        fontSize = 20.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = JakartaSans,         // Use JakartaSans
        fontWeight = FontWeight.Medium,   // Medium weight
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = JakartaSans,         // Use JakartaSans
        fontWeight = FontWeight.Normal,   // Regular weight
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = JakartaSans,         // Use JakartaSans
        fontWeight = FontWeight.Normal,   // Regular weight
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = JakartaSans,         // Use JakartaSans
        fontWeight = FontWeight.Medium,   // Medium weight
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    displayLarge = TextStyle(
        fontFamily = JakartaSans,         // Use JakartaSans
        fontWeight = FontWeight.Normal,   // Regular weight
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    )
)
