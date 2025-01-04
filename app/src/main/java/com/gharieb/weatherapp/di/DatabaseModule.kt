package com.gharieb.weatherapp.di

import android.app.Application
import androidx.room.Room
import com.gharieb.weather.room.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): WeatherDatabase =
        Room.databaseBuilder(application, WeatherDatabase::class.java, "weathers.database")
            .fallbackToDestructiveMigration()
            .build()

}
