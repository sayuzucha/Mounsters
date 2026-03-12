package com.example.mounsters.features.capture.domain.repository

interface CaptureRepository {

    suspend fun captureMonster(
        spawnId: String,
        monsterId: String
    )

}