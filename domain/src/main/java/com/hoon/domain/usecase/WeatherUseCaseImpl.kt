package com.hoon.domain.usecase

import com.hoon.domain.repository.WeatherRepository

class WeatherUseCaseImpl (private val repository: WeatherRepository): WeatherUseCase {
    override suspend operator fun invoke(lat: Double, lon: Double, exclude: String, appid: String) =
        repository.getWeather(lat, lon, exclude, appid)
}