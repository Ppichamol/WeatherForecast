package com.test.weatherforecastapp.data.remote

import com.test.weatherforecastapp.BuildConfig
import com.test.weatherforecastapp.model.CurrentWeatherDataResponse
import com.test.weatherforecastapp.model.GetGeoResponse
import com.test.weatherforecastapp.model.ThreeHoursWeatherDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("geo/1.0/direct")
    suspend fun getSearchGeoList(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5, // maximum for Free tier API
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): List<GetGeoResponse>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): CurrentWeatherDataResponse

    @GET("data/2.5/forecast")
    suspend fun getThreeHourlyWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): ThreeHoursWeatherDataResponse
}