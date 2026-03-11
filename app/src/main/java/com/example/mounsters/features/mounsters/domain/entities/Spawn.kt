package com.example.mounsters.features.mounsters.domain.entities

data class Spawn(
    val spawnId: String,
    val monster: Monster,   // entidad Monster del domain
    val lat: Double,
    val lng: Double,
    val expiresAt: String   // ISO 8601, por ejemplo "2024-01-01T10:15:00Z"
)