package com.test.weatherforecastapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DisplayCityModel(
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double
) : Parcelable
