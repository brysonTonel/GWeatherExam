package com.bryson.gweather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryson.gweather.domain.model.Weather
import com.bryson.gweather.domain.usecase.GetCurrentWeatherUseCase
import com.bryson.gweather.domain.usecase.GetWeatherHistoryUseCase
import com.bryson.gweather.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val fetchCurrentWeather: GetCurrentWeatherUseCase,
    private val getWeatherHistory: GetWeatherHistoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Weather>>(UiState.Loading)
    val state: StateFlow<UiState<Weather>> = _state

    fun loadCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _state.value = UiState.Loading
                fetchCurrentWeather(lat, lon)

                getWeatherHistory().collect { list ->
                    list.firstOrNull()?.let { weather ->
                        _state.value = UiState.Success(weather)
                    }
                }
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
