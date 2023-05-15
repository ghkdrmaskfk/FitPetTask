package com.hoon.fitpettask.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoon.data.BuildConfig
import com.hoon.data.utils.default
import com.hoon.data.weather.model.WeatherState
import com.hoon.domain.model.RecordItem
import com.hoon.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor (private val weatherUseCase: WeatherUseCase): ViewModel() {
    private val _weather: MutableStateFlow<WeatherState> = MutableStateFlow(WeatherState.Init)
    val weather: StateFlow<WeatherState> get() = _weather

    private val recordList = ArrayList<RecordItem>()

    suspend fun getWeather(lat: Double, lon: Double) =
        viewModelScope.launch {
            weatherUseCase(lat, lon, "current,minutely,hourly,alerts", BuildConfig.API_KEY)
                .onStart {
                    _weather.value = WeatherState.IsLoading(true)
                }.catch { exception ->
                    _weather.value = WeatherState.IsLoading(false)
                    _weather.value = WeatherState.Failure(exception.message.default())
                }
                .collect {
                    _weather.value = WeatherState.IsLoading(false)

                    recordList.addAll(it)

                    if (recordList.size % 21 == 0) {

                        _weather.value = WeatherState.Success(recordList)
                    }
                }
        }.join()

    private fun getSubDay(time: Long): String {
        val format = SimpleDateFormat("E d MMM", Locale.US)
        return format.format(time * 1000)
    }

    fun getTemperature(min: Float, max: Float): String {
        val minTemp = (min - 273.15).toInt()
        val maxTemp = (max - 273.15).toInt()

        return "Max : $maxTemp°C   Min : $minTemp°C"
    }

    fun intervalBetweenDate(itemTime: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val before = Date(itemTime * 1000)
        val today = Calendar.getInstance()

        val now = Date(today.time.time)

        val beforeTime = dateFormat.format(before)
        val nowTime = dateFormat.format(now)


        //현재 시간
        val nowFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nowTime)
        val beforeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeTime)
        val diffSec     = (nowFormat.time - beforeFormat.time) / 1000 //초 차이
        val diffDays    = diffSec / (24 * 60 * 60) //일 차이

        return when(diffDays) {
            0L -> { //오늘
                "Today"
            }
            -1L -> { //내일
                "Tomorrow"
            }
            else -> {
                getSubDay(itemTime)
            }
        }
    }

    fun updateWeatherIcon(condition: Int): String {
        when (condition) {
            in 200..299 -> {
                return "ic_wi_thunderstorm"
            }
            in 300..499 -> {
                return "ic_wi_day_rain"
            }
            in 500..599 -> {
                return "ic_wi_rain"
            }
            in 600..700 -> {
                return "ic_wi_snow"
            }
            in 701..771 -> {
                return "ic_wi_fog"
            }
            in 772..799 -> {
                return "ic_wi_day_sunny_overcast"
            }
            800 -> {
                return "ic_wi_day_sunny"
            }
            in 801..804 -> {
                return "ic_wi_cloudy"
            }
            in 900..902 -> {
                return "ic_wi_thunderstorm"
            }
            903 -> {
                return "ic_wi_snow"
            }
            904 -> {
                return "ic_wi_day_sunny"
            }
            else -> return if (condition in 905..1000) {
                "ic_wi_thunderstorm"
            } else "dunno"
        }
    }
}