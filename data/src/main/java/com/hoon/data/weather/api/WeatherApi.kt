package com.hoon.data.weather.api

import com.hoon.data.weather.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("onecall")
    suspend fun  getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude", encoded = true) exclude: String,
        @Query("appid", encoded = true) appid: String): WeatherModel

}