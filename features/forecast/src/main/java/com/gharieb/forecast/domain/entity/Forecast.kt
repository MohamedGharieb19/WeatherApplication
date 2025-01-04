package com.gharieb.forecast.domain.entity

import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location

data class Forecast(
    val current: Current?,
    val location: Location?,
    val forecast: ForecastList?
)














