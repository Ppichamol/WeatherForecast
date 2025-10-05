package com.test.weatherforecastapp.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ThreeHoursWeatherDataResponse(
    @SerializedName("city")
    var city: City? = City(),
    @SerializedName("cod")
    var cod: String? = null,
    @SerializedName("message")
    var message : Double? = null,
    @SerializedName("cnt")
    var cnt: Int? = null,
    @SerializedName("list")
    var weatherList: List<WeatherDataList> = listOf()
)
