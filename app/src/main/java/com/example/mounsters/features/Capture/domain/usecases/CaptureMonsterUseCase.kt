package com.example.mounsters.features.capture.domain.usecases

import com.example.mounsters.features.capture.domain.repository.CaptureRepository
import javax.inject.Inject

class CaptureMonsterUseCase @Inject constructor(
    private val repository: CaptureRepository
) {

    suspend operator fun invoke(
        spawnId: String,
        monsterId: String
    ) {

        repository.captureMonster(
            spawnId = spawnId,
            monsterId = monsterId
        )

    }

}
