package com.gharieb.weather.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location


@Entity(tableName = "weathers")
data class Weather(
    @PrimaryKey val id: Long = 0,
    val current: Current?,
    val location: Location?,
)














