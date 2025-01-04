package com.gharieb.forecast.data.model

import com.gharieb.core.data.model.CurrentDTO
import com.gharieb.core.data.model.LocationDTO

data class ForecastDTO(
    val current: CurrentDTO? = null,
    val location: LocationDTO? = null,
    val forecast: ForecastListDTO? = null,
)









