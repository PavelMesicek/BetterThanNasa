package com.moonlight.betterthannasa.presentation.screens.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MapScreen(navController: NavHostController) {
    Text(modifier = Modifier.fillMaxWidth(),text = "Map")
}