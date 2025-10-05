package com.test.weatherforecastapp.utils

import java.util.Locale

object ConvertCountryName {
    fun getCountryName(countryCode: String?): String {
        if (countryCode.isNullOrBlank()) return "-"
        return try {
            Locale.Builder()
                .setRegion(countryCode.uppercase())
                .build()
                .getDisplayCountry(Locale.ENGLISH)
        } catch (e: Exception) {
            "-"
        }
    }
}