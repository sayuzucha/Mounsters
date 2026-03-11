package com.example.mounsters.features.Capture.data.datasources.remote.models


data class CaptureStatsResponse(
    val success: Boolean,
    val data: CaptureStats
)

data class CaptureStats(
    val totalCaptures: Int,
    val uniqueMonsters: Int,
    val rarestMonster: String?,
    val lastCaptureAt: String?
)