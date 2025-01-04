package com.gharieb.core.di

import com.gharieb.core.domain.repository.location.ILocationTracker
import com.gharieb.core.domain.use_case.location.GetLocationTrackerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetLocationTrackerUseCase(iLocationTracker: ILocationTracker): GetLocationTrackerUseCase =
        GetLocationTrackerUseCase(iLocationTracker)

}