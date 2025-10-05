package com.test.weatherforecastapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object TimeUtil {
    fun formatDateShort(dateTime: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = inputFormat.parse(dateTime)
            val outputFormat = SimpleDateFormat("E d MMM", Locale.US)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            Log.d("DE_BUG_CITY", "error: ${e.message}")
            "-"
        }
    }

    fun formatCityLocal(
        dtUtcSec: Long,
        tzOffsetSec: Int,
        pattern: String = "yyyy-MM-dd HH:mm:ss"
    ): String {
        try {
            val localMillis = dtUtcSec * 1000L + (tzOffsetSec * 1000L)
            val sdf = SimpleDateFormat(pattern, Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            return sdf.format(Date(localMillis))
        } catch (e: Exception) {
            Log.d("DE_BUG_CITY", "error: ${e.message}")
            return ""
        }
    }
}