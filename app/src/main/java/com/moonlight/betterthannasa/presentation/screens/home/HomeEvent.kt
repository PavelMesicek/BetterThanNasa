package com.moonlight.betterthannasa.presentation.screens.home

sealed class HomeEvent{
    object Refresh: HomeEvent()
    object ToggleChangeView: HomeEvent()
}
