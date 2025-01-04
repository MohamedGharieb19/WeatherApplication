package com.gharieb.weatherapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gharieb.core.routes.Route
import com.gharieb.forecast.presentation.ForecastScreen
import com.gharieb.weather.presentation.CurrentWeatherScreen

@Composable
fun NavGraph(navController: NavHostController, contentPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = Route.CurrentWeather) {
        composable<Route.CurrentWeather> {
            CurrentWeatherScreen(navHostController = navController, contentPadding = contentPadding)
        }

        composable<Route.Forecast> {
            ForecastScreen(navHostController = navController)
        }
    }
}
