package com.moonlight.betterthannasa.presentation.screens.welcome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moonlight.betterthannasa.domain.repository.BetterThanNasaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: BetterThanNasaRepository
) : ViewModel() {

    private var _state = mutableStateOf(WelcomeState())
    val state: State<WelcomeState> = _state

    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.CompleteOnBoarding -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.saveOnBoardingState(completed = true)
                }
                _state.value = _state.value.copy(
                    isOnBoardingCompleted = !_state.value.isOnBoardingCompleted
                )
            }
        }
    }
}