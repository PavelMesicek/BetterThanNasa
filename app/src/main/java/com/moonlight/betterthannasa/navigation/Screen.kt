package com.moonlight.betterthannasa.navigation

sealed class Screen(val route: String){
    object Splash: Screen("splash_screen")
    object Welcome: Screen("welcome_screen")
    object Home: Screen("home_screen")
    object Map: Screen("map_screen")
    object Details: Screen("details_screen")
}
