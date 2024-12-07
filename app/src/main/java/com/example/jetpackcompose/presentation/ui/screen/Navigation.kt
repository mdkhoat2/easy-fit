package com.example.jetpackcompose.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
* Created by Duy on 29/11/2024
* @project EasyFit
* @author Duy
*/

object Navigation{
    val HOME_SCREEN = "home_screen"
}

@Composable
fun Onboarding(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Navigation.HOME_SCREEN){
        composable(Navigation.HOME_SCREEN){
            HomeScreen()
        }
    }
}
