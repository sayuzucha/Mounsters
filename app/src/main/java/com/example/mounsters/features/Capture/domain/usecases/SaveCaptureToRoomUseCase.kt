package com.example.mounsters.features.Capture.domain.usecases

import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.database.dao.MonsterDao
import com.example.mounsters.core.database.entities.CaptureEntity
import com.example.mounsters.core.database.entities.MonsterEntity
import com.example.mounsters.features.Capture.data.datasources.remote.models.CapturePostResponse
import com.example.mounsters.features.mounsters.data.datasources.remote.api.MonstersApiService
import javax.inject.Inject

class SaveCaptureToRoomUseCase @Inject constructor(
    private val captureDao: CaptureDao,
    private val monsterDao: MonsterDao,
    private val monstersApiService: MonstersApiService
) {
    suspend operator fun invoke(
        response: CapturePostResponse,
        userId: String
    ) {
        val capture = response.data ?: return

        // Guardar el monstruo en Room si no existe
        if (monsterDao.getMonsterById(capture.monsterId) == null) {
            // Pedir detalles completos del monstruo (incluye imageUrl)
            val imageUrl = try {
                val monsterResponse = monstersApiService.getMonsterDetail(capture.monsterId)
                monsterResponse.data.firstOrNull()?.imageUrl
            } catch (e: Exception) {
                null
            }

            monsterDao.insertMonster(
                MonsterEntity(
                    id          = capture.monsterId,
                    name        = capture.monster.name,
                    type        = capture.monster.type ?: "unknown",
                    rarity      = capture.monster.rarity,
                    baseHp      = capture.hp,
                    baseAttack  = capture.attack,
                    habitat     = "unknown",
                    imageUrl    = imageUrl,
                    description = null
                )
            )
        }

        // Guardar la captura en Room
        captureDao.insertCapture(
            CaptureEntity(
                id         = capture.id,
                userId     = userId,
                monsterId  = capture.monsterId,
                spawnId    = null,
                nickname   = capture.nickname,
                level      = capture.level,
                xp         = capture.xp ?: 0,
                hp         = capture.hp,
                attack     = capture.attack,
                lat        = capture.lat,
                lng        = capture.lng,
                capturedAt = capture.capturedAt
            )
        )
    }
}