package com.gharieb.forecast.di

import com.gharieb.forecast.domain.repository.remote.IForecastRepository
import com.gharieb.forecast.domain.use_case.GetForecastWeatherDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetForecastWeatherDataUseCase(iForecastRepository: IForecastRepository): GetForecastWeatherDataUseCase =
        GetForecastWeatherDataUseCase(iForecastRepository)
}