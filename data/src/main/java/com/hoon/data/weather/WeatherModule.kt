package com.hoon.data.weather

import com.hoon.data.module.NetworkModule
import com.hoon.data.weather.api.WeatherApi
import com.hoon.data.weather.datasource.WeatherRemoteDataSource
import com.hoon.data.weather.datasource.WeatherRemoteDataSourceImpl
import com.hoon.data.weather.repository.WeatherRepositoryImpl
import com.hoon.domain.repository.WeatherRepository
import com.hoon.domain.usecase.WeatherUseCase
import com.hoon.domain.usecase.WeatherUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class WeatherModule {
    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit) : WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(api: WeatherApi) : WeatherRemoteDataSource {
        return WeatherRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(dataSource: WeatherRemoteDataSource) : WeatherRepository {
        return WeatherRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideWeatherUseCase (repository: WeatherRepository) : WeatherUseCase {
        return WeatherUseCaseImpl(repository)
    }
}