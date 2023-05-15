package com.hoon.domain.model

sealed class RecordItem {
    abstract val id: Long

    data class Item(val item: WeatherInfoItem) : RecordItem() { // 아이템 항목
        override val id = item.dt
    }
    data class Header(val cityName: String) : RecordItem() { // 헤더
        override val id = Long.MIN_VALUE
    }
    object Bottom : RecordItem() { // 항목이 없을 때
        override val id = Long.MAX_VALUE
    }
}