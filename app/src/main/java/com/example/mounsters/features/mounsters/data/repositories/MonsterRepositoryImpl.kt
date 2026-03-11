package com.example.mounsters.features.mounsters.data.repositories

import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.mounsters.data.datasources.remote.api.MonstersApiService
import com.example.mounsters.features.mounsters.data.datasources.remote.mapper.toDomain
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.domain.repositories.MonsterRepository
import javax.inject.Inject

class MonsterRepositoryImpl @Inject constructor(
    private val api: MonstersApiService,
    private val tokenManager: TokenManager
) : MonsterRepository {

    override suspend fun getNearbySpawns(
        lat: Double,
        lng: Double,
        radius: Int
    ): List<Spawn> {

        // Obtener token guardado
        val token = tokenManager.getToken()
            ?: throw IllegalStateException("User not logged in, token missing")

        // Llamada a la API con Authorization
        val response = api.getNearbySpawns(
            lat = lat,
            lng = lng,
            radius = radius,
            authorization = "Bearer $token"
        )

        // Convertimos cada Spawn API a la entidad de dominio
        return response.data.map { it.toDomain() }
    }
}