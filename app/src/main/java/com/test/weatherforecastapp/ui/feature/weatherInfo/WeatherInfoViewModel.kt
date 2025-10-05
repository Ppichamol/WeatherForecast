package com.test.weatherforecastapp.ui.feature.weatherInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weatherforecastapp.model.DisplayHoursDataModel
import com.test.weatherforecastapp.data.repository.WeatherForecastRepository
import com.test.weatherforecastapp.utils.InternetConnection
import com.test.weatherforecastapp.utils.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val weatherForecastRepository : WeatherForecastRepository,
    private val internetConnection: InternetConnection
) : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherInfoModelUiState>(WeatherInfoModelUiState.LoadingState)
    val uiState: StateFlow<WeatherInfoModelUiState> = _uiState
    val isConnected = internetConnection.isConnected

    fun fetchWeatherInfo(lat: Double, lon: Double) {
        if (!isConnected.value) {
            _uiState.value = WeatherInfoModelUiState.EmptyState
            return
        }
        viewModelScope.launch {
            combine(
                weatherForecastRepository.getCurrentWeatherData(lat, lon),
                weatherForecastRepository.getThreeHourlyWeatherData(lat, lon)
            ) { currentData, hourlyData ->
                Pair(currentData, hourlyData)
            }.onStart {
                _uiState.value = WeatherInfoModelUiState.LoadingState
            }.catch { e ->
                _uiState.value = WeatherInfoModelUiState.FetchDataError
            }.collect { (currentData, threeHoursData) ->
                val threeHoursList = threeHoursData.weatherList

                if (threeHoursList.isNotEmpty() && currentData.weather.isNotEmpty()) {
                    val mapHourlyWeatherList = threeHoursList.map { item ->
                        DisplayHoursDataModel(
                            dateTime = item.dt ?: 0.toLong(),
                            dateTimeTxt = TimeUtil.formatCityLocal(
                                item.dt ?: 0L,
                                threeHoursData.city?.timezone ?: 0
                            ).ifBlank { "-:--" },
                            icon = item.weather[0].icon ?: "",
                            temp = item.main?.temp ?: 0.00,
                            minTemp = item.main?.tempMin ?: 0.00,
                            maxTemp = item.main?.tempMax ?: 0.00,
                        )
                    }

                    val currentWeatherData = DisplayHoursDataModel(
                        dateTime = currentData.dt ?: 0.toLong(),
                        dateTimeTxt = "now",
                        icon = currentData.weather[0].icon ?: "",
                        temp = currentData.main?.temp ?: 0.00,
                        title = currentData.weather[0].main ?: "-",
                        description = currentData.weather[0].description ?: "-",
                        minTemp = currentData.main?.tempMin ?: 0.00,
                        maxTemp = currentData.main?.tempMax ?: 0.00,
                        feelsLike = currentData.main?.feelsLike ?: 0.00,
                        humidity = currentData.main?.humidity ?: 0,
                        wind = currentData.wind?.speed ?: 0.00,
                        visibility = (currentData.visibility ?: 0)/ 1000,
                        pressureGround = currentData.main?.groundLevel ?: 0,
                        pressureSea = currentData.main?.seaLevel ?: 0,
                        sunriseRaw = currentData.sys?.sunrise ?: 0L,
                        sunrise = TimeUtil.formatCityLocal(
                            currentData.sys?.sunrise ?: 0L,
                            currentData.timezone ?: 0
                        ).ifBlank { "-:--" },
                        sunset = TimeUtil.formatCityLocal(
                            currentData.sys?.sunset ?: 0L,
                            currentData.timezone ?: 0
                        ).ifBlank { "-:--" }
                    )

                    val threeHoursDataList = listOf(currentWeatherData) + mapHourlyWeatherList.take(27)
                    val fiveDayForecast = findMinTempMaxTempOfEachDay(mapHourlyWeatherList)

                    _uiState.value = WeatherInfoModelUiState.FetchDataSuccess(
                        currentWeather = currentWeatherData,
                        weatherList = threeHoursDataList,
                        dailyForecast = fiveDayForecast,
                        cityName = currentData.name ?: "",
                        isDayTime = currentWeatherData.isDayTime()
                    )

                } else {
                    _uiState.value = WeatherInfoModelUiState.FetchDataError
                }
            }
        }
    }

    fun findMinTempMaxTempOfEachDay(
        hourlyWeatherList: List<DisplayHoursDataModel>
    ) : List<DisplayHoursDataModel> {
        return  hourlyWeatherList
            .groupBy { item ->
                item.dateTimeTxt.substring(0, 10)
            }
            .mapNotNull { (date, itemsForDay) ->
                if (itemsForDay.isEmpty()) return@mapNotNull null

                val minTemp = itemsForDay.minOf { it.minTemp }
                val maxTemp = itemsForDay.maxOf { it.maxTemp }
                val firstItem = itemsForDay.minByOrNull { it.dateTime }

                firstItem?.copy(
                    minTemp = minTemp,
                    maxTemp = maxTemp,
                    dateTimeTxt = date
                )
            }
            .take(5)
    }
}

sealed interface WeatherInfoModelUiState {
    object LoadingState: WeatherInfoModelUiState
    object EmptyState: WeatherInfoModelUiState
    object FetchDataError: WeatherInfoModelUiState
    data class FetchDataSuccess(
        val currentWeather: DisplayHoursDataModel,
        val weatherList: List<DisplayHoursDataModel>,
        val dailyForecast: List<DisplayHoursDataModel>,
        val cityName: String,
        val isDayTime: Boolean
    ) : WeatherInfoModelUiState
}