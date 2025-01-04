package com.gharieb.weather.data.repository.remote

import com.gharieb.core.utils.Resource
import com.gharieb.weather.data.source.remote.WeatherApi
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.mapper.toModel
import com.gharieb.weather.domain.repository.remote.IWeatherRemote
import javax.inject.Inject

class WeatherRemoteImpl@Inject constructor(
    private val api: WeatherApi,
): IWeatherRemote {
    override suspend fun getCurrentWeatherData(lat: Double?, long: Double?, city: String?): Resource<Weather> {
        return try {
            val value = city ?: "$lat,$long"
            Resource.Success(
                data = api.getCurrentWeatherData(
                    value = value
                ).toModel()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

}