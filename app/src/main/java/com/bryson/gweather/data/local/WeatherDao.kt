package com.bryson.gweather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)
    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastWeather(): WeatherEntity?
    @Query("SELECT * FROM weather ORDER BY timestamp DESC")
    fun getWeatherHistory(): Flow<List<WeatherEntity>>
}
