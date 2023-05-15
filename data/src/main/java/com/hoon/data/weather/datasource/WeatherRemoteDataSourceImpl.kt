package com.hoon.data.weather.datasource

import com.hoon.data.weather.api.WeatherApi
import com.hoon.data.weather.model.WeatherModel

class WeatherRemoteDataSourceImpl (private val api: WeatherApi): WeatherRemoteDataSource {
    override suspend fun getWeather(lat: Double, lon: Double, exclude: String, appid: String): WeatherModel = api.getWeather(lat, lon, exclude, appid)
}