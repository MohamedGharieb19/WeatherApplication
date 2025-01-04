package com.gharieb.weather.data.source.remote

import com.gharieb.core.constants.ApiConstants.API_KEY
import com.gharieb.core.constants.ApiConstants.CURRENT_WEATHER
import com.gharieb.weather.data.model.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(CURRENT_WEATHER)
    suspend fun getCurrentWeatherData(
        @Query("key") apiKey: String = API_KEY,
        @Query("q") value: String // query parameter cityName or latitude,longitude
    ): WeatherDTO

}