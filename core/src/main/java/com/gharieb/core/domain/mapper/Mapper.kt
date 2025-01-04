package com.gharieb.core.domain.mapper

import com.gharieb.core.data.model.ConditionDTO
import com.gharieb.core.data.model.CurrentDTO
import com.gharieb.core.data.model.LocationDTO
import com.gharieb.core.domain.entity.Condition
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location
import com.gharieb.weather_utils.DateFormatter.formatDateToToday


fun CurrentDTO.toModel(): Current {
    return Current(
        lastUpdated = lastUpdated?.let { formatDateToToday(it) }?:"",
        isDay = isDay == 1,
        temperature = temperature?.let { "$it°C" }?:"",
        condition = condition?.toModel()
    )
}

fun LocationDTO.toModel(): Location {
    return Location(
        localtime = localtime,
        name = name.plus(" $country"),
        longitude = longitude,
        latitude = latitude
    )
}



fun ConditionDTO.toModel(): Condition {
    return Condition(
        code = code,
        icon = "https:".plus(icon),
        text = text
    )
}