package com.moonlight.betterthannasa.presentation.screens.home

import com.moonlight.betterthannasa.domain.model.Meteorite

data class HomeState(
    val meteorites: List<Meteorite> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isMapVisible: Boolean = true,
    val error: String? = null
)