package com.test.weatherforecastapp.data.repository

import com.test.weatherforecastapp.data.repository.datasource.WeatherForecastDataSource
import com.test.weatherforecastapp.model.CurrentWeatherDataResponse
import com.test.weatherforecastapp.model.GetGeoResponse
import com.test.weatherforecastapp.model.ThreeHoursWeatherDataResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface WeatherForecastRepository {
    fun getSearchGeoList(
        cityName: String
    ): Flow<List<GetGeoResponse>>

    fun getCurrentWeatherData(
        lat: Double,
        lon: Double,
    ): Flow<CurrentWeatherDataResponse>

    fun getThreeHourlyWeatherData(
        lat: Double,
        lon: Double,
    ): Flow<ThreeHoursWeatherDataResponse>
}

class WeatherForecastRepositoryImpl(
    private val dataSource: WeatherForecastDataSource,
    private val dispatcher: CoroutineDispatcher
) : WeatherForecastRepository {

    override fun getSearchGeoList(
        cityName: String
    ) = dataSource.getSearchGeoList(cityName).flowOn(dispatcher)

    override fun getCurrentWeatherData(
        lat: Double,
        lon: Double,
    ) = dataSource.getCurrentWeatherData(lat, lon).flowOn(dispatcher)

    override fun getThreeHourlyWeatherData(
        lat: Double,
        lon: Double,
    ) = dataSource.getThreeHourlyWeatherData(lat, lon).flowOn(dispatcher)
}