package com.gharieb.forecast.domain.repository.remote

import com.gharieb.core.utils.Resource
import com.gharieb.forecast.domain.entity.Forecast

interface IForecastRepository {
    suspend fun getForecastWeatherData(lat: Double?=null, long: Double?=null,city: String?=null,days: String): Resource<Forecast>
}