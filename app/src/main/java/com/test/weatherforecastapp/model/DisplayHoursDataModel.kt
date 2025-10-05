package com.test.weatherforecastapp.model

import com.test.weatherforecastapp.utils.TimeUtil

object UrlConstants {
    const val IMAGE_URL = "https://openweathermap.org/img/wn/"
}

data class DisplayHoursDataModel(
    val dateTime: Long,
    val dateTimeTxt: String,
    val icon: String,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val title: String = "",
    val description: String = "",
    val feelsLike: Double = 0.0,
    val humidity: Int = 0,
    val wind: Double = 0.0,
    val visibility: Int = 0,
    val sunriseRaw: Long = 0L,
    val sunrise: String = "00:00",
    val sunset: String = "00:00",
    val pressureGround: Int = 0,
    val pressureSea: Int = 0,
) {
    fun setTempUnit(
        temp: Double,
        isCelsius: Boolean = false
    ): String {
        return if(isCelsius) (temp - 273.15).toInt().toString() + "°"
        else kelvinToFahrenheit(temp).toInt().toString() + "°"
    }

    fun kelvinToFahrenheit(kelvin: Double): Double = (kelvin - 273.15) * 9 / 5 + 32

    fun imageUrl(): String = "${UrlConstants.IMAGE_URL}$icon.png"

    fun getHourlyTime(): String = dateTimeTxt.split(" ").last().substringBeforeLast(":")

    fun getSunriseTime(): String = sunrise.split(" ").last().substringBeforeLast(":")

    fun getSunsetTime(): String = sunset.split(" ").last().substringBeforeLast(":")

    fun getDayTimeFormat(): String = TimeUtil.formatDateShort(dateTimeTxt)

    fun isDayTime(): Boolean {
        return dateTime >= sunriseRaw
    }
}
