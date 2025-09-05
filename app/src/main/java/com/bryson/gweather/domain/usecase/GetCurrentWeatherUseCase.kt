package com.bryson.gweather.domain.usecase


import com.bryson.gweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) {
        repository.fetchAndSaveWeather(lat, lon)
    }
}
