package com.gharieb.forecast.presentation

sealed class ForecastIntent {
    data class LoadCurrentWeather(val days: String) : ForecastIntent()
}