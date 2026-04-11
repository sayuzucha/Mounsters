package com.example.mounsters.features.Collection.domain.usecases

import com.example.mounsters.features.Collection.domain.entities.CapturedMonster
import com.example.mounsters.features.Collection.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    operator fun invoke(userId: String): Flow<List<CapturedMonster>> {
        return repository.getCollection(userId)
    }
}