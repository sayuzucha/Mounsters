package com.example.mounsters.features.Capture.data.repository

import com.example.mounsters.features.Capture.data.datasources.remote.api.CaptureApiService
import com.example.mounsters.features.Capture.data.datasources.remote.models.CapturePostResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureRequest
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureStatsResponse
import com.example.mounsters.features.Capture.domain.repository.CaptureRepository
import javax.inject.Inject

class CaptureRepositoryImpl @Inject constructor(
    private val api: CaptureApiService   // ← solo esto, el token va via AuthInterceptor
) : CaptureRepository {

    override suspend fun getMyCaptures(): CaptureResponse {
        return api.getMyCaptures()
    }

    override suspend fun getCaptureStats(): CaptureStatsResponse {
        return api.getCaptureStats()
    }

    override suspend fun captureMonster(request: CaptureRequest): CapturePostResponse {
        return api.captureMonster(request)
    }

    override suspend fun releaseMonster(id: String): CaptureResponse {
        return api.releaseMonster(id)
    }
}