package com.gharieb.weather.data.source.local

import androidx.room.TypeConverter
import com.gharieb.core.domain.entity.Current
import com.gharieb.core.domain.entity.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return gson.toJson(location)
    }

    @TypeConverter
    fun toLocation(json: String?): Location? {
        if (json == null) return null
        val type = object : TypeToken<Location>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromCurrent(current: Current?): String? {
        return gson.toJson(current)
    }

    @TypeConverter
    fun toCurrent(json: String?): Current? {
        if (json == null) return null
        val type = object : TypeToken<Current>() {}.type
        return gson.fromJson(json, type)
    }

}
