package com.moonlight.betterthannasa.presentation.screens.home

import android.location.Location
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moonlight.betterthannasa.domain.location.LocationTracker
import com.moonlight.betterthannasa.domain.repository.BetterThanNasaRepository
import com.moonlight.betterthannasa.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BetterThanNasaRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _currentLocation = MutableStateFlow(listOf(0.0, 0.0))
    val currentLocation: StateFlow<List<Double>> = _currentLocation

    private var _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        loaderMeteorites()
        getCurrentLocation()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> {
                loaderMeteorites(fetchFromRemote = true)
            }
            is HomeEvent.ToggleChangeView -> {
                _state.value = _state.value.copy(
                    isMapVisible = !_state.value.isMapVisible
                )
            }
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            locationTracker.getCurrentLocation()
                ?.let { location ->
                    _currentLocation.value = listOf(
                        location.latitude,
                        location.longitude
                    )
                }
        }
    }

    private fun loaderMeteorites(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()
                ?.let { location ->
                    repository.getMeteorites(fetchFromRemote)
                        .collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    result.data?.let { meteorites ->
                                        _state.value = state.value.copy(
                                            meteorites = meteorites.sortedBy { meteorite ->
                                                calculateDistance(
                                                    meteorite.latitude,
                                                    meteorite.longitude,
                                                    location.latitude,
                                                    location.longitude
                                                )
                                            }
                                        )
                                    }
                                }
                                is Resource.Error -> {
                                    _state.value = state.value.copy(
                                        meteorites = emptyList(),
                                        isLoading = false,
                                        error = result.message
                                    )
                                }
                            }
                        }
                } ?: kotlin.run {
                _state.value = state.value.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

    private fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        return results[0]
    }
}