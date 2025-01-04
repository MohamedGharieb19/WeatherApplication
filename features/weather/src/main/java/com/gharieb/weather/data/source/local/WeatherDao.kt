package com.gharieb.weather.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gharieb.weather.domain.entity.Weather


@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: Weather): Long

    @Query("SELECT * FROM weathers")
    fun getWeather(): List<Weather?>
}
