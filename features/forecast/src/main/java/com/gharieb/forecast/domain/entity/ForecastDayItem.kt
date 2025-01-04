package com.gharieb.forecast.domain.entity

data class ForecastDayItem(
    val date: String?,
    val day: Day?,
    val isToday: Boolean?
)
