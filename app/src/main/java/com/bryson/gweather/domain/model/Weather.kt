package com.bryson.gweather.domain.model

data class Weather(
    val city: String,
    val country: String,
    val temperature: Double,
    val sunrise: Long,
    val sunset: Long,
    val condition: String,
    val icon: String,
    val timestamp: Long
)