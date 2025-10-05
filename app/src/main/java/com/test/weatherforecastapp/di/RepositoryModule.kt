package com.test.weatherforecastapp.di

import com.test.weatherforecastapp.data.repository.datasource.WeatherForecastRemoteDataSource
import com.test.weatherforecastapp.data.repository.WeatherForecastRepository
import com.test.weatherforecastapp.data.repository.WeatherForecastRepositoryImpl
import com.test.weatherforecastapp.data.remote.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideHomeRepository(
        @IODispatcher dispatcher: CoroutineDispatcher,
        apiService: APIService,
    ): WeatherForecastRepository {
        return WeatherForecastRepositoryImpl(
            dataSource = WeatherForecastRemoteDataSource(
                apiService = apiService,
                dispatcher = dispatcher,
            ),
            dispatcher = dispatcher,
        )
    }
}