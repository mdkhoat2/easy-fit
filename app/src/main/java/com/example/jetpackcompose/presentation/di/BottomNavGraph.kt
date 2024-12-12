package com.example.jetpackcompose.presentation.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
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
    selectWorkoutViewModel: SelectWorkoutViewModel){
    var sessionTrackingState : SessionTrackingUIState = SessionTrackingUIState()
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
            SelectWorkoutsScreen(navController, viewModel = selectWorkoutViewModel)
        }
        composable(route = Routes.sessionTracking) {
            val getExerciseFromWorkoutUseCase = remember {
                getExerciseFromWorkoutUseCase(
                    WorkoutRepositoryImp(
                        WorkoutDatabase.getInstance(navController.context)
                    )
                )
            }

            val viewModel = SessionTrackingViewModel(getExerciseFromWorkoutUseCase)

            SessionTrackingScreen(navController, viewModel = viewModel){
                sessionTrackingState = viewModel.state.value
            }
        }
        composable(route = Routes.wellDone) {
            WellDoneScreen(navController, sessionTrackingState)
        }
    }
}