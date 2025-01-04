package com.gharieb.core.di

import com.gharieb.core.data.repository.location.LocationTrackerImpl
import com.gharieb.core.domain.repository.location.ILocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: LocationTrackerImpl): ILocationTracker
}