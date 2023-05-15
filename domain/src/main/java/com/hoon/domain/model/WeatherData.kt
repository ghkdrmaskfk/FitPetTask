package com.hoon.domain.model

data class WeatherInfoData(val timeZone : String,
                           val dailyItems: List<WeatherInfoItem>)

data class WeatherInfoItem(
    val dt: Long,
    val temp: WeatherInfoTemp,
    val weathers: List<WeatherInfo?>)

data class WeatherInfoTemp(
    val min: Float,
    val max: Float)

data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String)

data class WeatherRecordItem(
    val item: WeatherRecord,
    val header: Header,
    val Bottom: Bottom
)

data class WeatherRecord(val item: List<WeatherInfoItem>)
data class Header(val dt: Long, val cityName: String)
data class Bottom(val dt: Long)

