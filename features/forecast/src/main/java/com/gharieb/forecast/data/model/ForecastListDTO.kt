package com.gharieb.forecast.data.model

import com.squareup.moshi.Json

data class ForecastListDTO(
    @field:Json(name="forecastday")
    val forecastDayList: List<ForecastDayItemDTO>? = null
)
