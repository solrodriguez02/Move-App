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
            ProfileScreen()
        }

        composable(
            Screen.RoutineScreen.route,
            arguments = listOf(navArgument("id") {type = NavType.IntType}),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/routine?id={id}" }, navDeepLink { uriPattern = "$secureUri/routine?id={id}" }),
            ) {
            RoutineScreen()
        }


        /*

        composable("home") {
            HomeAuxScreen(onNavigateToOtherScreen = { id -> navController.navigate("other/$id")})
        }
        composable(
            route = "other/{id}",
            arguments = listOf(navArgument("id") {type = NavType.IntType}),
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/other?id={id}" }, navDeepLink { uriPattern = "$secureUri/other?id={id}" }),
        ) {route ->
            OtherScreen(id = route.arguments?.getInt("id"))
        }

         */
    }
}