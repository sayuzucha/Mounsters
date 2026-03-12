package com.example.mounsters.features.capture.data.repository

import com.example.mounsters.features.capture.data.remote.CaptureApiService
import com.example.mounsters.features.capture.data.remote.CaptureRequest
import com.example.mounsters.features.capture.domain.repository.CaptureRepository
import javax.inject.Inject

class CaptureRepositoryImpl @Inject constructor(
    private val api: CaptureApiService
) : CaptureRepository {

    override suspend fun captureMonster(
        spawnId: String,
        monsterId: String
    ) {

        api.captureMonster(
            CaptureRequest(
                spawnId = spawnId,
                monsterId = monsterId
            )
        )

    }

}