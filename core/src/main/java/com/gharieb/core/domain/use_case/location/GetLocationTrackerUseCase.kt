package com.gharieb.core.domain.use_case.location

import com.gharieb.core.domain.repository.location.ILocationTracker

class GetLocationTrackerUseCase(private val iLocationTracker: ILocationTracker) {
    suspend operator fun invoke() = iLocationTracker.getCurrentLocation()
}