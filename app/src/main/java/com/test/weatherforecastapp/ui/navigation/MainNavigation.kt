package com.test.weatherforecastapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.weatherforecastapp.model.DisplayCityModel
import com.test.weatherforecastapp.ui.feature.search.SearchScreen
import com.test.weatherforecastapp.ui.feature.weatherInfo.WeatherInfoScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.MAIN_ROUTE) {
        composable(
            route = NavRoute.MAIN_ROUTE
        ) {
            SearchScreen(
                onCitySelected = { cityInfo ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(NavArgument.CITY_ARG, cityInfo)
                    navController.navigate(NavRoute.WEATHER_DETAIL_ROUTE)
                }
            )
        }

        composable(
            route = NavRoute.WEATHER_DETAIL_ROUTE,
        ) { backStackEntry ->

            val cityInfo = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<DisplayCityModel>(NavArgument.CITY_ARG)

            WeatherInfoScreen(
                cityInfo = cityInfo,
                onBackPress = {
                    navController.popBackStack()
                }
            )
        }
    }
}

object NavRoute {
    const val MAIN_ROUTE = "main_route"
    const val WEATHER_DETAIL_ROUTE = "weather_forecast_route"
}

object NavArgument {
    const val CITY_ARG = "cityInfo"
}