package com.moonlight.betterthannasa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.moonlight.betterthannasa.presentation.screens.details.DetailsScreen
import com.moonlight.betterthannasa.presentation.screens.home.HomeScreen
import com.moonlight.betterthannasa.presentation.screens.map.MapScreen
import com.moonlight.betterthannasa.presentation.screens.splash.SplashScreen
import com.moonlight.betterthannasa.presentation.screens.welcome.WelcomeScreen
import com.moonlight.betterthannasa.util.Constants.DETAILS_ARGUMENT_KEY

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Map.route) {
            MapScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(navController = navController)
        }
    }
}