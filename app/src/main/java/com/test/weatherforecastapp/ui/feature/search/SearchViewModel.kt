package com.test.weatherforecastapp.ui.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weatherforecastapp.model.DisplayCityModel
import com.test.weatherforecastapp.data.repository.WeatherForecastRepository
import com.test.weatherforecastapp.utils.InternetConnection
import com.test.weatherforecastapp.utils.ConvertCountryName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherForecastRepository : WeatherForecastRepository,
    private val internetConnection: InternetConnection
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchModelUiState>(SearchModelUiState.InitState)
    val uiState: StateFlow<SearchModelUiState> = _uiState
    val isConnected = internetConnection.isConnected
    fun searchCityList(cityName: String) {
        viewModelScope.launch {
            weatherForecastRepository.getSearchGeoList(cityName = cityName)
                .onStart {
                    _uiState.value = SearchModelUiState.LoadingState
                }
                .catch { e ->
                    Log.d("DE_BUG_CITY", "Error: ${e.message}")
                    _uiState.value = SearchModelUiState.FetchDataError
                }
                .collect { cityList ->
                    if (cityList.isNotEmpty()) {
                        val showCityList = cityList.map { item ->

                            val countryName = ConvertCountryName
                                .getCountryName(countryCode = item.country ?: "")

                            DisplayCityModel(
                                name = item.name ?: "-",
                                country = countryName,
                                lat = item.lat ?: 0.0,
                                lon = item.lon ?: 0.0
                            )
                        }
                        _uiState.value = SearchModelUiState.FetchDataSuccess(showCityList)
                    } else {
                        _uiState.value = SearchModelUiState.NotFoundState
                    }
                    Log.d("DE_BUG_CITY", "count: ${cityList.count()}")
                }
        }
    }

    fun clearSearchList() {
        viewModelScope.launch {
            _uiState.value = SearchModelUiState.EmptyState
        }
    }
}

sealed interface SearchModelUiState {
    object InitState: SearchModelUiState
    object NotFoundState: SearchModelUiState
    object EmptyState: SearchModelUiState
    object LoadingState: SearchModelUiState
    object FetchDataError: SearchModelUiState
    data class FetchDataSuccess(val data:  List<DisplayCityModel>) : SearchModelUiState
}