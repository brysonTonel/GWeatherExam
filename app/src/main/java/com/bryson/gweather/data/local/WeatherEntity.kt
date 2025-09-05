package com.bryson.gweather.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val country: String,
    val temperature: Double,
    val sunrise: Long,
    val sunset: Long,
    val condition: String,
    val icon: String,
    val timestamp: Long
)
