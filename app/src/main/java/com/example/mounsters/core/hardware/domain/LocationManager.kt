package com.example.mounsters.core.hardware.domain

data class DeviceLocation(
    val lat: Double,
    val lng: Double
)

interface LocationManager {
    suspend fun getCurrentLocation(): DeviceLocation?
}