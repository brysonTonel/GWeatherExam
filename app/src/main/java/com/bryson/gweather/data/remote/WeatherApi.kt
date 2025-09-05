package com.bryson.gweather.data.remote

import com.bryson.gweather.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") key: String = BuildConfig.OPEN_WEATHER_KEY
    ): WeatherResponseDto
}