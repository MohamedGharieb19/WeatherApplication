package com.gharieb.weather.domain.mapper

import com.gharieb.core.domain.mapper.toModel
import com.gharieb.weather.data.model.WeatherDTO
import com.gharieb.weather.domain.entity.Weather

fun WeatherDTO.toModel(): Weather {
    return Weather(
        current = current?.toModel(),
        location = location?.toModel(),
    )
}




