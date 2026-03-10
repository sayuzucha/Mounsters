package com.example.mounsters.core.hardware.data

import android.annotation.SuppressLint
import android.content.Context

import com.example.mounsters.core.hardware.domain.DeviceLocation
import com.example.mounsters.core.hardware.domain.LocationManager
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidLocationManager @Inject constructor(
    @ApplicationContext context: Context
) : LocationManager {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): DeviceLocation? =
        suspendCancellableCoroutine { cont ->

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        cont.resume(
                            DeviceLocation(
                                lat = location.latitude,
                                lng = location.longitude
                            )
                        )
                    } else {
                        cont.resume(null)
                    }
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
}