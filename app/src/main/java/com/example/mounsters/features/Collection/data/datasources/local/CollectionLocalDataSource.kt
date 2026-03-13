package com.example.mounsters.features.Collection.data.datasources.local

import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.database.dao.MonsterDao
import com.example.mounsters.core.database.entities.CaptureEntity
import com.example.mounsters.features.Collection.domain.entities.CapturedMonster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CollectionLocalDataSource @Inject constructor(
    private val captureDao: CaptureDao,
    private val monsterDao: MonsterDao
) {
    fun getCollection(userId: String): Flow<List<CaptureEntity>> {
        return captureDao.getCapturesByUser(userId)
    }

    suspend fun saveCapture(capture: CaptureEntity) {
        captureDao.insertCapture(capture)
    }

    suspend fun deleteCapture(capture: CaptureEntity) {
        captureDao.deleteCapture(capture)
    }
}