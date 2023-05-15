package com.hoon.data.weather.model
import com.google.gson.annotations.SerializedName

data class WeatherModel(@SerializedName("timezone") val timeZone: String,
                        @SerializedName("daily") val dailyItems: List<WeatherItem>)

data class WeatherItem(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temp: Temp,
    @SerializedName("weather") val weathers: List<Weather?>)

data class Temp(
    @SerializedName("min") val min: Float,
    @SerializedName("max") val max: Float)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String)