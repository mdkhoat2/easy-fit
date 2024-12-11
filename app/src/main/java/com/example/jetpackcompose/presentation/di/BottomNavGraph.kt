package com.example.jetpackcompose.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.data.repo.WorkoutRepositoryProvider
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import com.example.jetpackcompose.presentation.ui.screen.AccountScreen
import com.example.jetpackcompose.presentation.ui.screen.ForumScreen
import com.example.jetpackcompose.presentation.ui.screen.HomeScreen
import com.example.jetpackcompose.presentation.ui.screen.PlanScreen
import com.example.jetpackcompose.presentation.ui.screen.SelectWorkoutsScreen
import com.example.jetpackcompose.presentation.ui.screen.SessionTrackingScreen
import com.example.jetpackcompose.presentation.ui.screen.WellDoneScreen
import com.example.jetpackcompose.presentation.ui.viewmodel.SelectWorkoutViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Plan.route) {
            PlanScreen()
        }
        composable(route = BottomBarScreen.Forum.route) {
            ForumScreen()
        }
        composable(route = BottomBarScreen.Account.route) {
            AccountScreen()
        }
        composable(route = Routes.selectWorkout) {
            val selectWorkoutViewModel = remember {
                val repository = WorkoutRepositoryProvider.repository
                val getYourWorkoutsUseCase = GetYourWorkoutsUseCase(repository)
                SelectWorkoutViewModel(getYourWorkoutsUseCase)
            }


            SelectWorkoutsScreen(navController, selectWorkoutViewModel)
        }
        composable(route = Routes.sessionTracking) {
            SessionTrackingScreen(navController)
        }
        composable(
            route = "well_done_screen/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) {
        }
    }
}