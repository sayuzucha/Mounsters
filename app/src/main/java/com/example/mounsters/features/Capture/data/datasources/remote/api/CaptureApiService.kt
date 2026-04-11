package com.example.mounsters.features.Capture.data.datasources.remote.api

import com.example.mounsters.features.Capture.data.datasources.remote.models.CapturePostResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureRequest
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureStatsResponse
import retrofit2.http.*

interface CaptureApiService {

    @GET("captures")
    suspend fun getMyCaptures(): CaptureResponse

    @GET("captures/stats")
    suspend fun getCaptureStats(): CaptureStatsResponse

    @POST("captures")
    suspend fun captureMonster(
        @Body request: CaptureRequest
    ): CapturePostResponse   // ← cambia aquí

    @PUT("captures/{id}")
    suspend fun updateCapture(
        @Path("id") id: String,
        @Body fields: Map<String, Any>
    ): CaptureResponse

    @DELETE("captures/{id}")
    suspend fun releaseMonster(@Path("id") id: String): CaptureResponse
}