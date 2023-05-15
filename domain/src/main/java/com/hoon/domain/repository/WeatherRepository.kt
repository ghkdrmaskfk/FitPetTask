package com.hoon.domain.repository

import com.hoon.domain.model.RecordItem
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double, exclude: String, appid: String): Flow<List<RecordItem>>
}