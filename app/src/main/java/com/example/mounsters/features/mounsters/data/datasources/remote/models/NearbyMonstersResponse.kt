package com.example.mounsters.features.mounsters.data.datasources.remote.models

data class NearbyMonstersResponse(
    val success: Boolean,
    val total: Int,
    val data: List<SpawnResponse>
)