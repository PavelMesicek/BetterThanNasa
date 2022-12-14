package com.moonlight.betterthannasa.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moonlight.betterthannasa.domain.repository.BetterThanNasaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: BetterThanNasaRepository
): ViewModel() {

    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingCompleted.value =
                repository.readOnBoardingState().stateIn(viewModelScope).value
        }
    }
}