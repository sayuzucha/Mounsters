package com.example.mounsters.features.Collection.domain.repository

import com.example.mounsters.features.Collection.domain.entities.CapturedMonster
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getCollection(userId: String): Flow<List<CapturedMonster>>
    suspend fun saveCapture(monster: CapturedMonster)
    suspend fun deleteCapture(id: String)
}