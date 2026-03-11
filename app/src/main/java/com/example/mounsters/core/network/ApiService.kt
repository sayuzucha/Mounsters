package com.example.mounsters.core.network

import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureRequest
import com.example.mounsters.features.Auth.data.datasources.remote.models.LoginResponse
import com.example.mounsters.features.Auth.data.datasources.remote.models.RegisterResponse

import com.example.mounsters.features.Auth.domain.entities.LoginRequest
import com.example.mounsters.features.Auth.domain.entities.RegisterRequest
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureStatsResponse
import com.example.mounsters.features.Notifications.data.datasources.remote.models.BaseResponse
import com.example.mounsters.features.Notifications.data.datasources.remote.models.NotificationResponse


import com.example.mounsters.features.spawns.data.datasources.remote.models.SpawnResponse

import com.example.mounsters.features.mounsters.data.datasources.remote.models.MonsterResponse

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // AUTH
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("auth/me")
    suspend fun getProfile(): LoginResponse


    // MONSTERS
    @GET("monsters")
    suspend fun getMonsters(): MonsterResponse

    @GET("monsters/{id}")
    suspend fun getMonsterById(
        @Path("id") id: String
    ): MonsterResponse

    @GET("monsters/nearby")
    suspend fun getNearbyMonsters(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int
    ): SpawnResponse


    // SPAWNS
    @GET("spawns")
    suspend fun getSpawns(
        @Query("lat") lat: Double?,
        @Query("lng") lng: Double?,
        @Query("radius") radius: Int?
    ): SpawnResponse


    // CAPTURES
    @GET("captures")
    suspend fun getMyCaptures(): CaptureResponse

    @GET("captures/stats")
    suspend fun getCaptureStats(): CaptureStatsResponse

    @POST("captures")
    suspend fun captureMonster(
        @Body request: CaptureRequest
    ): CaptureResponse


    // NOTIFICATIONS
    @GET("notifications")
    suspend fun getNotifications(
        @Query("read") read: Boolean?
    ): NotificationResponse

    @PUT("notifications/read-all")
    suspend fun markAllNotificationsRead(): BaseResponse
}