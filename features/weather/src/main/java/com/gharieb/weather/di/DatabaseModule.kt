package com.gharieb.weather.di

import com.gharieb.weather.data.source.local.WeatherDao
import com.gharieb.weather.room.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }
}