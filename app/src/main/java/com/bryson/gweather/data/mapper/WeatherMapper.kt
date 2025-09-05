package com.bryson.gweather.data.mapper

import com.bryson.gweather.data.local.WeatherEntity
import com.bryson.gweather.data.remote.WeatherResponseDto
import com.bryson.gweather.domain.model.Weather


fun WeatherResponseDto.toEntity(timestamp: Long): WeatherEntity {
    return WeatherEntity(
        city = name,
        country = sys.country,
        temperature = main.temp,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        condition = weather.firstOrNull()?.main ?: "",
        icon = weather.firstOrNull()?.icon ?: "",
        timestamp = timestamp
    )
}

fun WeatherEntity.toDomain(): Weather {
    return Weather(
        city = city,
        country = country,
        temperature = temperature,
        sunrise = sunrise,
        sunset = sunset,
        condition = condition,
        icon = icon,
        timestamp = timestamp
    )
}
