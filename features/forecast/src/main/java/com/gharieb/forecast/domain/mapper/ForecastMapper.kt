package com.gharieb.forecast.domain.mapper

import com.gharieb.core.domain.mapper.toModel
import com.gharieb.forecast.data.model.DayDTO
import com.gharieb.forecast.data.model.ForecastDTO
import com.gharieb.forecast.data.model.ForecastDayItemDTO
import com.gharieb.forecast.data.model.ForecastListDTO
import com.gharieb.forecast.domain.entity.Day
import com.gharieb.forecast.domain.entity.Forecast
import com.gharieb.forecast.domain.entity.ForecastDayItem
import com.gharieb.forecast.domain.entity.ForecastList
import com.gharieb.weather_utils.DateFormatter.formatDateToDayOrToday
import com.gharieb.weather_utils.DateFormatter.isToday
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun ForecastDTO.toModel(): Forecast {
    return Forecast(
        current = current?.toModel(),
        location = location?.toModel(),
        forecast = forecast?.toModel()
    )
}

fun ForecastListDTO.toModel(): ForecastList {
    return ForecastList(
        forecastDayList = forecastDayList!!.map { it.toModel() }
    )
}

fun ForecastDayItemDTO.toModel(): ForecastDayItem {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getDefault()

    val dateTimestamp = date?.let { inputFormat.parse(it) }

    return ForecastDayItem(
        date = date?.let { formatDateToDayOrToday(it) },
        isToday = dateTimestamp?.let { isToday(it) } ?: false,
        day = day?.toModel()
    )
}

fun DayDTO.toModel(): Day {
    return Day(
        averageTemperature = averageTemperature?.let { "$it°C" } ?:"",
        condition = condition?.toModel()
    )
}