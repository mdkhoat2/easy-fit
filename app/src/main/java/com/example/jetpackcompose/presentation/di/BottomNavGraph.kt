package com.example.jetpackcompose.presentation.di

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import com.example.jetpackcompose.domain.usecase.getExerciseFromWorkoutUseCase
import com.example.jetpackcompose.presentation.ui.screen.WellDoneScreen
import com.example.jetpackcompose.presentation.ui.UIState.SessionTrackingUIState
import com.example.jetpackcompose.presentation.ui.screen.AccountScreen
import com.example.jetpackcompose.presentation.ui.screen.ForumScreen
import com.example.jetpackcompose.presentation.ui.screen.HomeScreen
import com.example.jetpackcompose.presentation.ui.screen.PlanScreen
import com.example.jetpackcompose.presentation.ui.screen.SelectWorkoutsScreen
import com.example.jetpackcompose.presentation.ui.screen.SessionTrackingScreen
import com.example.jetpackcompose.presentation.ui.viewmodel.SelectWorkoutViewModel
import com.example.jetpackcompose.presentation.ui.viewmodel.SessionTrackingViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    context: Context = LocalContext.current
    ){
    var sessionTrackingState = SessionTrackingUIState()
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController, context)
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
            SelectWorkoutsScreen(navController, context)
        }
        composable(
            route = "${Routes.sessionTracking}/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getString("workoutId")
            requireNotNull(workoutId) { "Workout ID must be provided" }

            Log.d("BottomNavGraph", "Navigated with workoutId: $workoutId")

            // Create use case
            val getExerciseFromWorkoutUseCase = remember {
                getExerciseFromWorkoutUseCase(
                    WorkoutRepositoryImp(
                        WorkoutDatabase.getInstance(navController.context)
                    )
                )
            }

            val viewModel = remember {
                SessionTrackingViewModel(getExerciseFromWorkoutUseCase, workoutId)
            }

            SessionTrackingScreen(navController, viewModel = viewModel) {
                sessionTrackingState = viewModel.state.value
            }
        }

        composable(route = Routes.wellDone) {
            WellDoneScreen(navController, sessionTrackingState)
        }
    }
}