package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.jetpackcompose.presentation.ui.screen.MainScreen
import com.example.jetpackcompose.ui.theme.JetPackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetPackComposeTheme {
                MainScreen()
            }
        }

        val calendar = Calendar.getInstance() // Get the current time
        calendar.add(Calendar.SECOND, 15)     // Add 15 seconds to the current time

        NotificationScheduler.getInstance().scheduleNotification(
            this,
            calendar.timeInMillis,
            "Reminder",
            "This is your scheduled notification!"
        )
    }
}
