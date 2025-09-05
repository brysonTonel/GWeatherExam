package com.bryson.gweather.data.repository

import android.util.Log
import com.bryson.gweather.data.local.WeatherDao
import com.bryson.gweather.domain.model.Weather
import com.bryson.gweather.data.mapper.toDomain
import com.bryson.gweather.data.mapper.toEntity
import com.bryson.gweather.data.remote.WeatherApi
import com.bryson.gweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override suspend fun fetchAndSaveWeather(lat: Double, lon: Double) {
        val dto = api.getCurrentWeather(lat, lon)
        val newTimestamp = System.currentTimeMillis()
        val newEntity = dto.toEntity(newTimestamp)


        val lastEntity = dao.getLastWeather()

        val shouldInsert = if (lastEntity == null) {
            true
        } else {
            val lastMinute = (lastEntity.timestamp / 60000)
            val newMinute = (newTimestamp / 60000)
            newMinute != lastMinute
        }

        if (shouldInsert) {
            dao.insertWeather(newEntity)
        }
    }

    override fun getWeatherHistory(): Flow<List<Weather>> {
        return dao.getWeatherHistory().map { list ->
            list.map { it.toDomain() }
        }
    }
}
