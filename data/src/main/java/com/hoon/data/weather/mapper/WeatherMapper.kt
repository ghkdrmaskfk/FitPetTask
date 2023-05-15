package com.hoon.data.weather.mapper
import com.hoon.data.weather.model.Temp
import com.hoon.data.weather.model.Weather
import com.hoon.data.weather.model.WeatherItem
import com.hoon.data.weather.model.WeatherModel
import com.hoon.domain.model.RecordItem
import com.hoon.domain.model.WeatherInfo
import com.hoon.domain.model.WeatherInfoItem
import com.hoon.domain.model.WeatherInfoTemp

fun WeatherModel.mapperToWeatherRecordItem(): List<RecordItem> {
    val list = arrayListOf<RecordItem>()
    list.add(RecordItem.Header(timeZone.split("/")[1]))
    dailyItems.subList(0, 5).mapperToWeatherInfoItem().forEach {
        list.add(RecordItem.Item(it))
    }
    list.add(RecordItem.Bottom)
    return list
}

fun List<WeatherItem>.mapperToWeatherInfoItem(): List<WeatherInfoItem> {
    val list = arrayListOf<WeatherInfoItem>()

    forEach {
        list.add(WeatherInfoItem(it.dt, it.temp.mapperToWeatherInfoTempData(), it.weathers.mapperToWeatherInfoData()))
    }
    return list
}

fun Temp.mapperToWeatherInfoTempData(): WeatherInfoTemp = WeatherInfoTemp(min, max)

fun List<Weather?>.mapperToWeatherInfoData(): List<WeatherInfo> {
    val list = arrayListOf<WeatherInfo>()

    forEach {
        it?.let {
            list.add(WeatherInfo(it.id, it.main, it.description, it.icon))
        }
    }
    return list
}