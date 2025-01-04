package com.gharieb.weather.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gharieb.weather.data.source.local.WeatherDao
import com.gharieb.weather.domain.entity.Weather


@Database(entities = [Weather::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}