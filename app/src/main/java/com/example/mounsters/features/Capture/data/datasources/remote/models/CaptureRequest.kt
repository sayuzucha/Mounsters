package com.example.mounsters.features.Capture.data.datasources.remote.models

data class CaptureRequest(
    val monsterId: String,
    val spawnId: String,
    val lat: Double,
    val lng: Double,
    val nickname: String
)