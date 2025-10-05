package com.test.weatherforecastapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response

private const val PLATFORM_HEADER = "agent"

class PlatformInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header(PLATFORM_HEADER, "android")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}