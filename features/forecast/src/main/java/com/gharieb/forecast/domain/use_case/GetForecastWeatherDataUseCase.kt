package com.gharieb.forecast.domain.use_case

import com.gharieb.forecast.domain.repository.remote.IForecastRepository

class GetForecastWeatherDataUseCase(private val iForecastRepository: IForecastRepository) {
    suspend operator fun invoke(
        lat: Double? = null,
        long: Double? = null,
        city: String? = null,
        days: String
    ) = iForecastRepository.getForecastWeatherData(lat = lat, long = long, city = city, days = days)

}