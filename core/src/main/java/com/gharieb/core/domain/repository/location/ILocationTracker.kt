package com.gharieb.core.domain.repository.location

import android.location.Location

interface ILocationTracker {
    suspend fun getCurrentLocation(): Location?
}