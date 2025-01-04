package com.gharieb.weather.data.model

import com.gharieb.core.data.model.CurrentDTO
import com.gharieb.core.data.model.LocationDTO


data class WeatherDTO(
	val current: CurrentDTO? = null,
	val location: LocationDTO? = null,
)









