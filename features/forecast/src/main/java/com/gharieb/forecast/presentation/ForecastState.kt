package com.gharieb.forecast.presentation

import com.gharieb.forecast.domain.entity.Forecast

data class ForecastState(
    val cityNameBySearch: String? = null,
    val forecastInfo: Forecast? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
