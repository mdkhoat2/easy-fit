package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import com.example.jetpackcompose.presentation.ui.screen.MainScreen
import com.example.jetpackcompose.presentation.ui.screen.SelectWorkoutsScreen
import com.example.jetpackcompose.ui.theme.JetPackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val workoutRepository = WorkoutRepositoryImp(WorkoutApi(),WorkoutDatabase()) // Replace with your implementation
        val getYourWorkoutsUseCase = GetYourWorkoutsUseCase(workoutRepository)
        setContent {
            JetPackComposeTheme {
                SelectWorkoutsScreen(

                    getWorkouts = {
                        GetYourWorkoutsUseCase(workoutRepository).invoke()
                    }
                )
            }
        }
    }
}
