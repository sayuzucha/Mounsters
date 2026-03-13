package com.example.mounsters.features.Collection.data.repository

import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.database.dao.MonsterDao
import com.example.mounsters.core.database.entities.CaptureEntity
import com.example.mounsters.core.database.entities.MonsterEntity
import com.example.mounsters.features.Collection.data.datasources.local.CollectionLocalDataSource
import com.example.mounsters.features.Collection.domain.entities.CapturedMonster
import com.example.mounsters.features.Collection.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val localDataSource: CollectionLocalDataSource,
    private val monsterDao: MonsterDao
) : CollectionRepository {

    override fun getCollection(userId: String): Flow<List<CapturedMonster>> {
        return localDataSource.getCollection(userId).map { captures ->
            captures.mapNotNull { capture ->
                val monster = monsterDao.getMonsterById(capture.monsterId)
                monster?.let {
                    CapturedMonster(
                        id          = capture.id,
                        monsterId   = capture.monsterId,
                        nickname    = capture.nickname,
                        level       = capture.level,
                        xp          = capture.xp,
                        hp          = capture.hp,
                        attack      = capture.attack,
                        monsterName = it.name,
                        monsterType = it.type,
                        monsterRarity = it.rarity,
                        imageUrl    = it.imageUrl ?: "",
                        capturedAt  = capture.capturedAt
                    )
                }
            }
        }
    }

    override suspend fun saveCapture(monster: CapturedMonster) {
        localDataSource.saveCapture(
            CaptureEntity(
                id         = monster.id,
                userId     = "",
                monsterId  = monster.monsterId,
                spawnId    = null,
                nickname   = monster.nickname,
                level      = monster.level,
                xp         = monster.xp,
                hp         = monster.hp,
                attack     = monster.attack,
                lat        = 0.0,
                lng        = 0.0,
                capturedAt = monster.capturedAt
            )
        )
    }

    override suspend fun deleteCapture(id: String) {
        // implementar si necesitas liberar monstruos
    }
}