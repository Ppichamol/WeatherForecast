package com.test.weatherforecastapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataList (
    @SerializedName("dt")
    var dt: Long? = null,
    @SerializedName("weather")
    var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("main")
    var main: Main? = Main(),
    @SerializedName("clouds")
    var clouds: Clouds? = Clouds(),
    @SerializedName("wind")
    var wind: Wind? = Wind(),
    @SerializedName("visibility")
    var visibility: Int? = null,
    @SerializedName("pop")
    var pop: Double? = null,
    @SerializedName("sys")
    var sys: Sys? = Sys(),
    @SerializedName("dt_txt")
    var dtTxt: String? = null
)
@Serializable
data class Wind (
    @SerializedName("speed")
    var speed: Double? = null,
    @SerializedName("deg")
    var deg: Int? = null,
    @SerializedName("gust")
    var gust: Double? = null
)

@Serializable
data class Rain (
    @SerializedName("1h")
    var oneHour: Double? = null,
)

@Serializable
data class Sys (
    @SerializedName("pod")
    var pod: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("sunrise")
    var sunrise: Long? = null,
    @SerializedName("sunset")
    var sunset: Long? = null

)

@Serializable
data class Clouds (
    @SerializedName("all")
    var all: Int? = null
)

@Serializable
data class Main (
    @SerializedName("temp")
    var temp: Double? = null,
    @SerializedName("feels_like")
    var feelsLike: Double? = null,
    @SerializedName("temp_min")
    var tempMin: Double? = null,
    @SerializedName("temp_max")
    var tempMax: Double? = null,
    @SerializedName("pressure")
    var pressure: Int? = null,
    @SerializedName("sea_level")
    var seaLevel: Int? = null,
    @SerializedName("grnd_level")
    var groundLevel: Int? = null,
    @SerializedName("humidity")
    var humidity: Int? = null,
    @SerializedName("temp_kf")
    var tempKf: Double? = null
)

