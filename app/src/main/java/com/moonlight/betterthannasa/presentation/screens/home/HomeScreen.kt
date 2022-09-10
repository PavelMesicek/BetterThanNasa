package com.moonlight.betterthannasa.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.moonlight.betterthannasa.R
import com.moonlight.betterthannasa.ui.theme.LARGE_PADDING

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val currentLocation = viewModel.currentLocation.collectAsState()
    val currentLatLng = LatLng(
        currentLocation.value.first(),
        currentLocation.value.last()
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLatLng,
            10f
        )
    }

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.value.isRefreshing
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(HomeEvent.ToggleChangeView)
            }) {
                Icon(
                    painter = if (viewModel.state.value.isMapVisible) {
                        painterResource(id = R.drawable.ic_toggle_off)
                    } else
                        painterResource(id = R.drawable.ic_toggle_on),
                    contentDescription = "Switch Between Views"
                )
            }
        }
    ) {
        if (viewModel.state.value.isMapVisible) {
            if (viewModel.state.value.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                }
            }
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onEvent(HomeEvent.Refresh)
                }
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewModel.state.value.meteorites.size) { i ->
                        val meteorite = viewModel.state.value.meteorites[i]
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colors.surface)
                                .padding(all = LARGE_PADDING)
                        ) {
                            MeteoriteItem(meteorite = meteorite)
                        }
                    }
                }
            }
        } else {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                val meteorites = viewModel.state.value.meteorites
                Marker(
                    state = MarkerState(currentLatLng),
                    title = "My location",
                    alpha = 1f
                )
                meteorites.forEach { meteorite ->
                    MeteoriteMarker(
                        position = LatLng(
                            meteorite.latitude,
                            meteorite.longitude
                        ),
                        title = meteorite.name,
                    )
                }
            }
        }
    }
}

@Composable
fun MeteoriteMarker(
    position: LatLng,
    title: String
) {
    val markerState = rememberMarkerState(
        null,
        position
    )
    Marker(
        state = markerState,
        title = title,
        alpha = 0.5f
    )
    markerState.showInfoWindow()
}