package com.gharieb.forecast.data.repository

import android.content.Context
import com.gharieb.core.data.source.local.shared_preference.SharedPreferencesHelper
import com.gharieb.core.utils.Resource
import com.gharieb.forecast.data.source.ForecastApi
import com.gharieb.forecast.domain.entity.Forecast
import com.gharieb.forecast.domain.mapper.toModel
import com.gharieb.forecast.domain.repository.remote.IForecastRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val api: ForecastApi,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    @ApplicationContext private val context: Context
): IForecastRepository {

    override suspend fun getForecastWeatherData(lat: Double?, long: Double?, city: String?, days: String): Resource<Forecast> {
        return try {
            val value = if (sharedPreferencesHelper.getHasUserSearchedCity(context = context)){
                sharedPreferencesHelper.getSearchedCityName(context = context)!!
            } else city ?: "$lat,$long"

            Resource.Success(
                data = api.getForecastWeatherData(
                    value = value,
                    forecastDays = days
                ).toModel()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}