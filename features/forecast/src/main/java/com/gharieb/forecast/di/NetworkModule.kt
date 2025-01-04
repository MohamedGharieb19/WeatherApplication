package com.gharieb.forecast.di

import com.gharieb.forecast.data.source.ForecastApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideForecastApi(retrofit: Retrofit): ForecastApi = retrofit.create(ForecastApi::class.java)
}