package com.hoon.data.weather.datasource

import com.hoon.data.weather.model.WeatherModel

interface WeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double, exclude: String, appid: String): WeatherModel
}