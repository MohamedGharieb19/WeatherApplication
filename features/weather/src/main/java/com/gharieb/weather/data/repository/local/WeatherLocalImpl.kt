package com.gharieb.weather.data.repository.local

import com.gharieb.core.utils.Resource
import com.gharieb.weather.data.source.local.WeatherDao
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.repository.local.IWeatherLocal
import javax.inject.Inject

class WeatherLocalImpl @Inject constructor(
    private val weatherDao: WeatherDao,
): IWeatherLocal {

    override suspend fun saveWeather(weather: Weather) {
        weatherDao.insertWeather(weather)
    }

    override suspend fun getSavedWeather(): Resource<Weather> {
        return try {
            Resource.Success(weatherDao.getWeather().first())
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}