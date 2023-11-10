package com.example.move

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink


@Composable
fun MoveNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val uri = "http://www.move.com"
    val secureUri = "https://www.move.com"

    NavHost(
        navController = navController, 
        startDestination = Screen.ExploreScreen.route
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
            SignInScreen(onNavigateToExplore = { navController.navigate("explore") })
        }
    }
}