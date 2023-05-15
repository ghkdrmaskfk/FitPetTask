package com.hoon.data.weather.model

import com.hoon.domain.model.RecordItem


sealed class WeatherState {
    object Init : WeatherState()
    data class IsLoading(val isLoading: Boolean) : WeatherState()
    data class Success(val recordList: List<RecordItem>) : WeatherState()
    data class Failure(val message: String) : WeatherState()
}