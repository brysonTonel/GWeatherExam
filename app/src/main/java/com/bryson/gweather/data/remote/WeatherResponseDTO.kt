package com.bryson.gweather.data.remote

data class WeatherResponseDto(
    val coord: CoordDto,
    val weather: List<WeatherDto>,
    val base: String,
    val main: MainDto,
    val visibility: Int,
    val wind: WindDto,
    val clouds: CloudsDto,
    val dt: Long,
    val sys: SysDto,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

data class CoordDto(val lon: Double, val lat: Double)

data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainDto(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int?,
    val grnd_level: Int?
)

data class WindDto(
    val speed: Double,
    val deg: Int,
    val gust: Double?
)

data class CloudsDto(val all: Int)

data class SysDto(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
