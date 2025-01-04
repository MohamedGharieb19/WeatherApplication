package com.gharieb.forecast.di

import com.gharieb.forecast.data.repository.ForecastRepositoryImpl
import com.gharieb.forecast.domain.repository.remote.IForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindForecastRepository(
        forecastRepositoryImpl: ForecastRepositoryImpl
    ): IForecastRepository
}