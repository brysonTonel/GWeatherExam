package com.bryson.gweather.domain.repository

import com.bryson.gweather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchAndSaveWeather(lat: Double, lon: Double)
    fun getWeatherHistory(): Flow<List<Weather>>
}