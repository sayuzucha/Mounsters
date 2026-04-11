package com.example.mounsters.features.Capture.domain.usecases

import com.example.mounsters.features.Capture.data.datasources.remote.models.CapturePostResponse
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureRequest
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureResponse
import com.example.mounsters.features.Capture.domain.repository.CaptureRepository
import javax.inject.Inject

class CaptureMonsterUseCase @Inject constructor(
    private val repository: CaptureRepository
) {
    suspend operator fun invoke(request: CaptureRequest): Result<CapturePostResponse> {
        return try {
            Result.success(repository.captureMonster(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}