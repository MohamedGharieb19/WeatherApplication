package com.gharieb.core.di

import android.content.Context
import android.content.SharedPreferences
import com.gharieb.core.constants.AppConstants.APP_NAME
import com.gharieb.core.data.source.local.shared_preference.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

        @Provides
        @Singleton
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
        }

        @Provides
        @Singleton
        fun provideSharedPreferencesHelper(
            @ApplicationContext context: Context )
                : SharedPreferencesHelper {
            return SharedPreferencesHelper(provideSharedPreferences(context))
        }

}