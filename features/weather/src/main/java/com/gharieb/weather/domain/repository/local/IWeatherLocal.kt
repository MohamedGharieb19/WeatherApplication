package com.gharieb.weather.domain.repository.local

import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather

interface IWeatherLocal {
    suspend fun saveWeather(weather: Weather)
    suspend fun getSavedWeather(): Resource<Weather>
}