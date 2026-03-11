package com.example.mounsters.features.Capture.data.datasources.remote.models

data class CaptureResponse(
    val success: Boolean,
    val total: Int?,
    val data: List<Capture>? = null
)

data class Capture(
    val id: String,
    val userId: String?,
    val monsterId: String,
    val nickname: String,
    val level: Int,
    val xp: Int?,
    val hp: Int,
    val attack: Int,
    val lat: Double,
    val lng: Double,
    val capturedAt: String,
    val monster: CaptureMonster
)

data class CaptureMonster(
    val name: String,
    val type: String?,
    val rarity: String
)