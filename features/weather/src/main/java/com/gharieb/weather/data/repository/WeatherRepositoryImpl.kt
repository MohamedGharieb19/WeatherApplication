package com.gharieb.weather.data.repository

import android.content.Context
import com.gharieb.core.data.source.local.shared_preference.SharedPreferencesHelper
import com.gharieb.core.utils.Resource
import com.gharieb.weather.domain.entity.Weather
import com.gharieb.weather.domain.repository.IWeatherRepository
import com.gharieb.weather.domain.repository.local.IWeatherLocal
import com.gharieb.weather.domain.repository.remote.IWeatherRemote
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remote: IWeatherRemote,
    private val local: IWeatherLocal,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext private val context: Context
): IWeatherRepository {

    override suspend fun getWeather(lat: Double?, long: Double?, city: String?): Resource<Weather> {
        return city?.let {
            remote.getCurrentWeatherData(city = city).also { weather ->
                weather.data?.let { it1 -> local.saveWeather(it1) }
            }
        }?:run {
            if (sharedPreferencesHelper.getHasUserSearchedCity(context)){
                local.getSavedWeather()
            } else {
                remote.getCurrentWeatherData(lat = lat, long = long)
            }
        }
    }
}