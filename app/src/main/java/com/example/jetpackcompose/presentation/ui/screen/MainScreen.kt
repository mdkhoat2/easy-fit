package com.example.jetpackcompose.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.R
<<<<<<< Updated upstream
import com.example.jetpackcompose.data.api.WorkoutApi
import com.example.jetpackcompose.data.database.WorkoutDatabase
import com.example.jetpackcompose.data.repo.WorkoutRepositoryImp
import com.example.jetpackcompose.domain.usecase.GetYourWorkoutsUseCase
=======
>>>>>>> Stashed changes
import com.example.jetpackcompose.presentation.di.BottomBarScreen
import com.example.jetpackcompose.presentation.di.BottomNavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val selectWorkoutViewModel = remember {
        val getYourWorkoutsUseCase = GetYourWorkoutsUseCase(
            WorkoutRepositoryImp(
                WorkoutApi(),
                WorkoutDatabase()
            )
        )
        SelectWorkoutViewModel(getYourWorkoutsUseCase)
    }

    Scaffold(
        bottomBar = {BottomBar(navController = navController)},
        contentColor = Color.White,
        containerColor = Color.Black
    ){
        BottomNavGraph(navController = navController, selectWorkoutViewModel = selectWorkoutViewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Plan,
        BottomBarScreen.Forum,
        BottomBarScreen.Account
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (currentDestination?.route !in screens.map { it.route }){
        return
    }

    NavigationBar(
        containerColor = colorFromResource(R.color.bottom_bar_background)
    ){
        screens.forEach{ screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun colorFromResource(resource: Int): Color{
    val context = LocalContext.current
    val colorInt = ContextCompat.getColor(context, resource)
    return Color(colorInt)
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Green,
            selectedTextColor = Color.Green,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.White,
            indicatorColor = colorFromResource(R.color.bottom_bar_background)
        )
    )
}