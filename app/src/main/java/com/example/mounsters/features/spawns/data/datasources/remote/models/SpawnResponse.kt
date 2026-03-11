package com.example.mounsters.features.spawns.data.datasources.remote.models

data class SpawnResponse(
    val success: Boolean,
    val total: Int,
    val data: List<Spawn>
)

data class Spawn(
    val id: String?,
    val spawnId: String?,
    val monsterId: String?,
    val lat: Double,
    val lng: Double,
    val active: Boolean?,
    val spawnedAt: String?,
    val expiresAt: String?,
    val monster: SpawnMonster
)

data class SpawnMonster(
    val id: String?,
    val name: String,
    val type: String?,
    val rarity: String?
)