package com.example.jetpackcompose.presentation.di

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.AddMissedDaysToHistoryUseCase
import com.example.jetpackcompose.domain.usecase.CreateWorkoutUseCase
import com.example.jetpackcompose.domain.usecase.GetExerciseFromWorkoutUseCase
import com.example.jetpackcompose.presentation.ui.screen.Home.WellDoneScreen
import com.example.jetpackcompose.presentation.ui.uiState.SessionTrackingUIState
import com.example.jetpackcompose.presentation.ui.screen.Account.AccountScreen
import com.example.jetpackcompose.presentation.ui.screen.Authen.ForgotMail
import com.example.jetpackcompose.presentation.ui.screen.Authen.ForgotOTP
import com.example.jetpackcompose.presentation.ui.screen.Authen.LoginScreen
import com.example.jetpackcompose.presentation.ui.screen.Authen.Register
import com.example.jetpackcompose.presentation.ui.screen.Forum.ForumScreen
import com.example.jetpackcompose.presentation.ui.screen.Home.HomeScreen
import com.example.jetpackcompose.presentation.ui.screen.Home.SelectWorkoutsScreen
import com.example.jetpackcompose.presentation.ui.screen.Home.SessionTrackingScreen
import com.example.jetpackcompose.presentation.ui.screen.Plan.CustomizeExercise
import com.example.jetpackcompose.presentation.ui.screen.Plan.EditPlan
import com.example.jetpackcompose.presentation.ui.screen.Plan.NewWorkoutScreen
import com.example.jetpackcompose.presentation.ui.screen.PlanScreen
import com.example.jetpackcompose.presentation.ui.uiState.WorkoutEditUIState
import com.example.jetpackcompose.presentation.ui.viewmodel.NewWorkoutViewModel
import com.example.jetpackcompose.presentation.ui.viewmodel.SessionTrackingViewModel
import com.example.jetpackcompose.util.initializeForFirstTimeUser

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    context: Context = LocalContext.current
){
    var sessionTrackingState = SessionTrackingUIState()

    // Initialize WorkoutDatabase asynchronously
    val workoutDatabaseState = produceState<WorkoutDatabase?>(initialValue = null) {
        value = WorkoutDatabase.getInstance(context)
    }

    when (val workoutDatabase = workoutDatabaseState.value) {
        null -> {
            Log.d("BottomNavGraph", "Initializing WorkoutDatabase")
            // Show a loading indicator while the database is being initialized
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        else -> {
            Log.d("BottomNavGraph", "WorkoutDatabase initialized")

            LaunchedEffect(workoutDatabase) {
                // Initialize default values for first-time users
                initializeForFirstTimeUser(context)
                AddMissedDaysToHistoryUseCase(WorkoutRepositoryImp(workoutDatabase, context)).invoke(context)
            }

            val workoutViewModel = remember {
                NewWorkoutViewModel(
                    CreateWorkoutUseCase(
                        WorkoutRepositoryImp(context = context, database = workoutDatabase)
                    )
                )
            }

            // Proceed once the database is initialized
            NavHost(
                navController = navController,
                startDestination = Routes.login
            ) {
                composable(route = Routes.login){
                    LoginScreen(navController)
                }
                composable(route = Routes.forgotEmail){
                    ForgotMail(navController)
                }
                composable(route = Routes.forgotOTP){
                    ForgotOTP(navController)
                }
                composable(route = Routes.register){
                    Register(navController)
                }
                composable(route = BottomBarScreen.Home.route) {
                    HomeScreen(navController, context, workoutDatabase)
                }
                composable(route = BottomBarScreen.Plan.route) {
                    PlanScreen(navController, context, workoutDatabase)
                }
                composable(route = BottomBarScreen.Forum.route) {
                    ForumScreen()
                }
                composable(route = BottomBarScreen.Account.route) {
                    AccountScreen()
                }
                composable(route = Routes.selectWorkout) {
                    SelectWorkoutsScreen(navController, workoutDatabase, context)
                }
                composable(
                    route = "${Routes.sessionTracking}/{workoutId}",
                    arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val workoutId = backStackEntry.arguments?.getString("workoutId")
                    requireNotNull(workoutId) { "Workout ID must be provided" }

                    val getExerciseFromWorkoutUseCase = remember {
                        GetExerciseFromWorkoutUseCase(
                            WorkoutRepositoryImp(workoutDatabase,context)
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
                    WellDoneScreen(navController, sessionTrackingState,workoutDatabase, context)
                }

                composable(route = Routes.newWorkout) {

                    NewWorkoutScreen(
                        navController = navController,
                        viewModel = workoutViewModel
                    )
                }

                composable(
                    route = "${Routes.CustomizeExercise}/{exerciseIndex}",
                    arguments = listOf(navArgument("exerciseIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    val exerciseIndex = backStackEntry.arguments?.getInt("exerciseIndex")
                    requireNotNull(exerciseIndex) { "Exercise index must be provided" }

                    CustomizeExercise(
                        navController = navController,
                        viewModel = workoutViewModel,
                        exerciseIndex = exerciseIndex,
                    )
                }

                composable(
                    route = Routes.editPlan
                ) {
                    EditPlan(navController, workoutDatabase, context                    )
                }

            }
        }
    }
}