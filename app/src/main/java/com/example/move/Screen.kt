package com.example.move
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector, val route: String) {
    object ExploreScreen: Screen("Explore", Icons.Filled.Search, "explore")
    object HomeScreen: Screen("Home", Icons.Filled.Home, "home")
    object ProfileScreen: Screen("Profile", Icons.Filled.AccountCircle, "profile/{id}")
    object RoutineScreen: Screen("Routine", Icons.Filled.Info, "routine/{id}")
    object RoutineExecutionScreen: Screen("Routine execution", Icons.Filled.PlayArrow, "routine/{id}/execute")
    object RoutineFinishedScreen: Screen("Finished routine", Icons.Filled.CheckCircle, "routine/{id}/finished")
    object SignInScreen: Screen("Sign in", Icons.Filled.AccountCircle, "signin")

}