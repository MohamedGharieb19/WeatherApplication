package com.gharieb.weather.data.repository

import android.content.Context
import com.gharieb.core.data.source.local.shared_preference.SharedPreferencesHelper
import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.repository.IWeatherRepository
import com.gharieb.weather.domain.repository.local.IWeatherLocal
import com.gharieb.weather.domain.repository.remote.IWeatherRemote
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remote: IWeatherRemote,
    private val local: IWeatherLocal,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext private val context: Context
): IWeatherRepository {

    override suspend fun getWeather(lat: Double?, long: Double?, city: String?): Resource<Weather> {
        return if (!city.isNullOrEmpty()) {
            // Fetch weather by city
            remote.getCurrentWeatherData(city = city).also { weather ->
                weather.data?.let { weatherData ->
                    withContext(Dispatchers.IO) {
                        local.saveWeather(weatherData)
                    }
                }
            }
        } else {
            if (sharedPreferencesHelper.getHasUserSearchedCity(context)) {
                // Fetch saved weather if it exists
                withContext(Dispatchers.IO) {
                    local.getSavedWeather()
                }
            } else {
                // Fetch weather by location
                remote.getCurrentWeatherData(lat = lat, long = long)
            }
        }
    }

}