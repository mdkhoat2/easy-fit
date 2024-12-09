package com.example.jetpackcompose.presentation.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
import com.example.jetpackcompose.presentation.ui.screen.AccountScreen
import com.example.jetpackcompose.presentation.ui.screen.ForumScreen
import com.example.jetpackcompose.presentation.ui.screen.HomeScreen
import com.example.jetpackcompose.presentation.ui.screen.PlanScreen
import com.example.jetpackcompose.presentation.ui.screen.SelectWorkoutsScreen
import com.example.jetpackcompose.presentation.ui.screen.WellDoneScreen
import com.example.jetpackcompose.presentation.ui.viewmodel.SelectWorkoutViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    selectWorkoutViewModel: SelectWorkoutViewModel
){
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
                onReturnHomeClick = { navController.navigate("home_screen") }
            )
        }

        //data class Workout(
        //    val id: String,
        //    val creatorId: User?,
        //    val name: String,
        //    val exercises: List<Exercise>,
        //    val duration: Int // Total duration in minutes
        //)
        //
        //data class Exercise(
        //    val name: ExerciseName,
        //    val type: ExerciseType = ExerciseType.COUNTED,
        //    val repetition: Int = 10, // Number of repetitions for "counted" exercises
        //    val duration: Int = 0, // Duration in seconds for "timed" exercises
        //    val restTime: Int = 15 // Rest time between sets in seconds
        //)


    }
}