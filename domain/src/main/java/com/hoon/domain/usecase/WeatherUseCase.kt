package com.hoon.domain.usecase

import com.hoon.domain.model.RecordItem
import kotlinx.coroutines.flow.Flow

interface WeatherUseCase {
    suspend operator fun invoke(lat: Double, lon: Double, exclude: String, appid: String): Flow<List<RecordItem>>
}