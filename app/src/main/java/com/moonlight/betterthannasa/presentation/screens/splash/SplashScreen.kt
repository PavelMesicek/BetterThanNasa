package com.moonlight.betterthannasa.presentation.screens.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.moonlight.betterthannasa.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val onBoardingCompleted by splashViewModel.onBoardingCompleted.collectAsState()
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.popBackStack()
        if (onBoardingCompleted) {
            navController.navigate(Screen.Home.route)
        } else {
            navController.navigate(Screen.Welcome.route)
        }
    }
    Loader()
}

@Composable
fun Loader() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(com.moonlight.betterthannasa.R.raw.asteroid)
    )

    LottieAnimation(
        modifier = Modifier
            .padding(all = 50.dp)
            .fillMaxSize(),
        alignment = Alignment.Center,
        composition = composition,
        iterations = Int.MAX_VALUE,
        speed = 2f
    )

}


