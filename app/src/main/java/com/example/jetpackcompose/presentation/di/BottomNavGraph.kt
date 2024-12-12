package com.example.jetpackcompose.presentation.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
<<<<<<< Updated upstream
import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
=======
import com.example.jetpackcompose.data.repo.WorkoutRepositoryProvider
>>>>>>> Stashed changes
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
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
<<<<<<< Updated upstream
            SelectWorkoutsScreen(navController, viewModel = selectWorkoutViewModel)
=======
            val selectWorkoutViewModel = remember {
                val repository = WorkoutRepositoryProvider.repository
                val getYourWorkoutsUseCase = GetYourWorkoutsUseCase(repository)
                SelectWorkoutViewModel(getYourWorkoutsUseCase)
            }

            SelectWorkoutsScreen(navController, selectWorkoutViewModel)
>>>>>>> Stashed changes
        }
        composable(route = Routes.sessionTracking) {
            val viewModel = SessionTrackingViewModel()
            SessionTrackingScreen(navController, viewModel = viewModel)
        }
        composable(
            route = "well_done_screen/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getString("workoutId") ?: return@composable
            val workout = selectWorkoutViewModel.getWorkoutById(workoutId) // Call the method from the ViewModel
            WellDoneScreen(
                streak = 10,
                exercises = workout.name,
                duration = workout.duration.toString(),
                exerciseDetails = workout.exercises.map { it.name.toString() to it.repetition.toString() },
                onSaveReceiptClick = { /* Save receipt action */ },
                onReturnHomeClick = { navController.navigate(BottomBarScreen.Home.route) }
            )
        }
    }
}