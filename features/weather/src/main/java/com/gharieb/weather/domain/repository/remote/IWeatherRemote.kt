package com.gharieb.weather.domain.repository.remote

import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather

interface IWeatherRemote {
    suspend fun getCurrentWeatherData(lat: Double?=null, long: Double?=null,city: String?=null): Resource<Weather>
}