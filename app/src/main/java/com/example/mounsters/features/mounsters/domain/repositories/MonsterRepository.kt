package com.example.mounsters.features.mounsters.domain.repositories

import com.example.mounsters.features.mounsters.domain.entities.Spawn

interface MonsterRepository {

    suspend fun getNearbySpawns(
        lat: Double,
        lng: Double,
        radius: Int
    ): List<Spawn>
}