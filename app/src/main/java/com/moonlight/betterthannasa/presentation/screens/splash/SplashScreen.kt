package com.moonlight.betterthannasa.presentation.screens.splash

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(navController: NavHostController) {
    Text(modifier = Modifier.fillMaxWidth(),text = "Splash")
}