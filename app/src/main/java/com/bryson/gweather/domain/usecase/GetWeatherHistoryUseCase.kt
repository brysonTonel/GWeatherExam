package com.bryson.gweather.domain.usecase

import com.bryson.gweather.domain.model.Weather
import com.bryson.gweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherHistoryUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(): Flow<List<Weather>> {
        return repository.getWeatherHistory()
    }
}
