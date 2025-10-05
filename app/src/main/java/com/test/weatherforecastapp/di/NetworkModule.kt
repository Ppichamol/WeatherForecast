package com.test.weatherforecastapp.di

import com.test.weatherforecastapp.data.remote.APIService
import com.test.weatherforecastapp.data.remote.PlatformInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRefreshTokenService(): APIService {
        val converterFactory = GsonConverterFactory.create()
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(PlatformInterceptor())
        }.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
            .create(APIService::class.java)
    }
}