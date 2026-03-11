package com.example.mounsters.features.mounsters.data.datasources.remote.api

import com.example.mounsters.features.mounsters.data.datasources.remote.models.NearbyMonstersResponse
import com.example.mounsters.features.mounsters.data.datasources.remote.models.MonsterResponse
import com.example.mounsters.features.mounsters.data.datasources.remote.models.SpawnResponse
import com.example.mounsters.features.mounsters.data.datasources.remote.models.Spawn
import retrofit2.http.*

interface MonstersApiService {

    // -------------------
    // MONSTERS
    // -------------------

    @GET("/monsters")
    suspend fun getMonsters(
        @Query("type") type: String? = null,
        @Query("rarity") rarity: String? = null,
        @Query("habitat") habitat: String? = null
    ): MonsterResponse

    @GET("/monsters/{id}")
    suspend fun getMonsterDetail(
        @Path("id") monsterId: String
    ): MonsterResponse

    @POST("/monsters")
    suspend fun createMonster(
        @Body monster: Map<String, Any>
    ): MonsterResponse

    @PUT("/monsters/{id}")
    suspend fun updateMonster(
        @Path("id") monsterId: String,
        @Body fields: Map<String, Any>
    ): MonsterResponse

    @DELETE("/monsters/{id}")
    suspend fun deleteMonster(
        @Path("id") monsterId: String
    ): MonsterResponse

    // -------------------
    // SPAWNS
    // -------------------

    @GET("/spawns")
    suspend fun getSpawns(
        @Query("lat") lat: Double? = null,
        @Query("lng") lng: Double? = null,
        @Query("radius") radius: Int? = null
    ): SpawnResponse

    @GET("/monsters/nearby")
    suspend fun getNearbySpawns(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int,
        @Header("Authorization") authorization: String
    ): SpawnResponse

    @POST("/spawns")
    suspend fun createSpawn(
        @Body spawn: Map<String, Any>
    ): SpawnResponse

    @PUT("/spawns/{id}")
    suspend fun updateSpawn(
        @Path("id") spawnId: String,
        @Body fields: Map<String, Any>
    ): SpawnResponse

    @DELETE("/spawns/{id}")
    suspend fun deleteSpawn(
        @Path("id") spawnId: String
    ): SpawnResponse
}