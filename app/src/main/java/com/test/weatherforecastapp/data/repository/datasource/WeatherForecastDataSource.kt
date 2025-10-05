package com.test.weatherforecastapp.data.repository.datasource

import com.test.weatherforecastapp.model.GetGeoResponse
import com.test.weatherforecastapp.model.ThreeHoursWeatherDataResponse
import com.test.weatherforecastapp.data.remote.APIService
import com.test.weatherforecastapp.model.CurrentWeatherDataResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface WeatherForecastDataSource {
    fun getSearchGeoList(
    cityName: String,
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

class WeatherForecastRemoteDataSource(
    private val apiService: APIService,
    private val dispatcher: CoroutineDispatcher
) : WeatherForecastDataSource {

    override fun getSearchGeoList(
    cityName: String,
    ) = flow { emit(apiService.getSearchGeoList(cityName)) }.flowOn(dispatcher)

    override fun getCurrentWeatherData(
        lat: Double,
        lon: Double,
    ) = flow { emit(apiService.getCurrentWeatherData(lat, lon)) }.flowOn(dispatcher)

    override fun getThreeHourlyWeatherData(
        lat: Double,
        lon: Double,
    ) = flow { emit(apiService.getThreeHourlyWeatherData(lat, lon)) }.flowOn(dispatcher)
}