package com.gharieb.weather.domain.repository

import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather

interface IWeatherRepository {
    suspend fun getWeather(lat: Double?=null, long: Double?=null,city: String?=null): Resource<Weather>
}