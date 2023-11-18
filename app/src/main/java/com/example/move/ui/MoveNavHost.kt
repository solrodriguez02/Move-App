package com.example.move.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.move.util.getViewModelFactory


@Composable
fun MoveNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val uri = "http://www.move.com"
    val secureUri = "https://www.move.com"
    val viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
    val uiState = viewModel.uiState
    val isAuthenticated = uiState.isAuthenticated

    NavHost(
        navController = navController, 
        startDestination = if(isAuthenticated) Screen.ExploreScreen.route else Screen.SignInScreen.route
    ) {
        composable(Screen.ExploreScreen.route) {
            ExploreScreen(
                onNavigateToProfile = { id -> navController.navigate("profile/$id") },
                onNavigateToRoutine = { id -> navController.navigate("routine/$id") }
                )
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateToProfile = { id -> navController.navigate("profile/$id") },
                onNavigateToRoutine = { id -> navController.navigate("routine/$id") }
                )
        }
        composable(
            Screen.ProfileScreen.route,
            arguments = listOf(navArgument("id") {type = NavType.IntType}),
            ) {
            ProfileScreen(navController = navController)
        }

        composable(
            Screen.RoutineScreen.route,
            arguments = listOf(navArgument("id") {type = NavType.IntType}),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/routine?id={id}" }, navDeepLink { uriPattern = "$secureUri/routine?id={id}" }),
            ) {
            RoutineScreen(
                onNavigateToExecute = { id -> navController.navigate("routine/$id/execute") },
                navController = navController
            )
        }

        composable(
            Screen.RoutineExecutionScreen.route,
            arguments = listOf(navArgument("id") {type = NavType.IntType}),
        ) {
            RoutineExecutionScreen(
                onNavigateToFinish = { id -> navController.navigate("routine/$id/finished") },
                navController = navController
            )
        }

        composable(
            Screen.RoutineFinishedScreen.route,
            arguments = listOf(navArgument("id") {type = NavType.IntType}),
        ) {
            FinishedRoutineScreen(onNavigateToHome = { navController.navigate("home") { popUpTo("explore") {inclusive = true} } })
        }

        composable(Screen.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
    }
}