package com.example.mounsters.features.Capture.domain.repository

import com.example.mounsters.features.Capture.data.datasources.remote.models.CapturePostResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureRequest
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureStatsResponse

interface CaptureRepository {
    suspend fun getMyCaptures(): CaptureResponse
    suspend fun getCaptureStats(): CaptureStatsResponse
    suspend fun captureMonster(request: CaptureRequest): CapturePostResponse  // ← cambia
    suspend fun releaseMonster(id: String): CaptureResponse
}