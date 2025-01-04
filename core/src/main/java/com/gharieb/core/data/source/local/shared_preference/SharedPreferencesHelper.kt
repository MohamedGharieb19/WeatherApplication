package com.gharieb.core.data.source.local.shared_preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.gharieb.core.constants.AppConstants.APP_NAME
import com.gharieb.core.constants.AppConstants.SEARCHED_CITY_NAME
import com.gharieb.core.constants.AppConstants.USER_HAS_SEARCHED_CITY
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor( val sharedPreferences: SharedPreferences) {

    fun <T> put( key: String, `object`: T) {

        val jsonString = GsonBuilder().create().toJson(`object`)

        sharedPreferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {

        val value = sharedPreferences.getString(key, null)

        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun setHasUserSearchedCity(context: Context, hasSearchedCity: Boolean) {
        context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(USER_HAS_SEARCHED_CITY, hasSearchedCity).apply()
    }

    fun getHasUserSearchedCity(context: Context): Boolean {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
            .getBoolean(USER_HAS_SEARCHED_CITY,false)
    }

    fun setSearchedCityName(context: Context, cityName: String) {
        context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
            .edit().putString(SEARCHED_CITY_NAME, cityName).apply()
    }

    fun getSearchedCityName(context: Context): String? {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
            .getString(SEARCHED_CITY_NAME,"")
    }



}