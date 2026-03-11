package com.example.mounsters.features.mounsters.domain.usecases

import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.domain.repositories.MonsterRepository
import javax.inject.Inject

class GetNearbySpawnsUseCase @Inject constructor(
    private val repository: MonsterRepository
) {

    suspend operator fun invoke(
        lat: Double,
        lng: Double,
        radius: Int = 500
    ): List<Spawn> {
        // El repositorio se encarga de tomar el token desde TokenManager
        return repository.getNearbySpawns(
            lat = lat,
            lng = lng,
            radius = radius
        )
    }
}