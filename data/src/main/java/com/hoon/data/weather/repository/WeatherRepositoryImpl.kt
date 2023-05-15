package com.hoon.data.weather.repository

import com.hoon.data.weather.datasource.WeatherRemoteDataSource
import com.hoon.data.weather.mapper.mapperToWeatherRecordItem
import com.hoon.domain.model.RecordItem
import com.hoon.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class WeatherRepositoryImpl (private val dataSource: WeatherRemoteDataSource): WeatherRepository {
        override suspend fun getWeather(lat: Double, lon: Double, exclude: String, appid: String): Flow<List<RecordItem>> = flow {
            emit(dataSource.getWeather(lat, lon, exclude, appid).mapperToWeatherRecordItem())
        }
}