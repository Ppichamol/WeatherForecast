package com.test.weatherforecastapp.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDataResponse(
    @SerializedName("coord")
    var coordination: Coordination? = Coordination(),
    @SerializedName("weather")
    var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("main")
    var main: Main? = Main(),
    @SerializedName("visibility")
    var visibility: Int? = null,
    @SerializedName("clouds")
    var clouds: Clouds? = Clouds(),
    @SerializedName("wind")
    var wind: Wind? = Wind(),
    @SerializedName("rain")
    var rain: Rain? = Rain(),
    @SerializedName("dt")
    var dt: Long? = null,
    @SerializedName("sys")
    var sys: Sys? = Sys(),
    @SerializedName("timezone")
    var timezone: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("cod")
    var cod: String? = null,
)
