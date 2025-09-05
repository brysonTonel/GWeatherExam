package com.bryson.gweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryson.gweather.domain.model.Weather
import com.bryson.gweather.domain.usecase.GetWeatherHistoryUseCase
import com.bryson.gweather.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val getWeatherHistory: GetWeatherHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Weather>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Weather>>> = _state

    init {
        viewModelScope.launch {
            try {
                getWeatherHistory().collectLatest { list ->
                    _state.value = UiState.Success(list)
                }
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
