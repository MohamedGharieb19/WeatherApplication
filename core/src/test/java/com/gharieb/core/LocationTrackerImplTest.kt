package com.gharieb.core

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.gharieb.core.data.repository.location.LocationTrackerImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Tasks
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class LocationTrackerImplTest {

    @Mock
    private lateinit var mockLocationClient: FusedLocationProviderClient

    @Mock
    private lateinit var mockApplication: Application

    @Mock
    private lateinit var mockLocationManager: LocationManager

    private lateinit var locationTracker: LocationTrackerImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockApplication.getSystemService(Context.LOCATION_SERVICE))
            .thenReturn(mockLocationManager)

        locationTracker = LocationTrackerImpl(mockLocationClient, mockApplication)
    }

    @Test
    fun `return null when permissions are not granted`() = runTest {
        Mockito.`when`(
            ContextCompat.checkSelfPermission(
                mockApplication,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ).thenReturn(PackageManager.PERMISSION_DENIED)

        val location = locationTracker.getCurrentLocation()
        assertNull(location)
    }

    @Test
    fun `return null when GPS is disabled`() = runTest {
        Mockito.`when`(mockLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            .thenReturn(false)

        val location = locationTracker.getCurrentLocation()
        assertNull(location)
    }

    @Test
    fun `return location when permissions and GPS are enabled`() = runTest {
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        Mockito.`when`(
            ContextCompat.checkSelfPermission(
                mockApplication,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ).thenReturn(PackageManager.PERMISSION_GRANTED)

        Mockito.`when`(mockLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            .thenReturn(true)

        Mockito.`when`(mockLocationClient.lastLocation)
            .thenReturn(Tasks.forResult(mockLocation))

        val location = locationTracker.getCurrentLocation()
        assertNotNull(location)
        assertEquals(LocationManager.GPS_PROVIDER, location?.provider)
    }

    @Test
    fun `return null when location retrieval fails`() = runTest {
        Mockito.`when`(mockLocationClient.lastLocation)
            .thenReturn(Tasks.forException(Exception("Error")))

        val location = locationTracker.getCurrentLocation()
        assertNull(location)
    }
}