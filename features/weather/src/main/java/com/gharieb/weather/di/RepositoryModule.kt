package com.gharieb.weather.di

import com.gharieb.weather.data.repository.WeatherRepositoryImpl
import com.gharieb.weather.data.repository.local.WeatherLocalImpl
import com.gharieb.weather.data.repository.remote.WeatherRemoteImpl
import com.gharieb.weather.domain.repository.IWeatherRepository
import com.gharieb.weather.domain.repository.local.IWeatherLocal
import com.gharieb.weather.domain.repository.remote.IWeatherRemote
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
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): IWeatherRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRemoteRepository(
        weatherRepositoryImpl: WeatherRemoteImpl
    ): IWeatherRemote

    @Binds
    @Singleton
    abstract fun bindWeatherLocalRepository(
        weatherRepositoryImpl: WeatherLocalImpl
    ): IWeatherLocal

}