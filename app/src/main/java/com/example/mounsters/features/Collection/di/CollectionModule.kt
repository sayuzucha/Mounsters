package com.example.mounsters.features.Collection.di

import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.database.dao.MonsterDao
import com.example.mounsters.features.Collection.data.datasources.local.CollectionLocalDataSource
import com.example.mounsters.features.Collection.data.repository.CollectionRepositoryImpl
import com.example.mounsters.features.Collection.domain.repository.CollectionRepository
import com.example.mounsters.features.Collection.domain.usecases.GetCollectionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CollectionModule {

    @Provides
    @Singleton
    fun provideCollectionLocalDataSource(
        captureDao: CaptureDao,
        monsterDao: MonsterDao
    ): CollectionLocalDataSource {
        return CollectionLocalDataSource(captureDao, monsterDao)
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(
        localDataSource: CollectionLocalDataSource,
        monsterDao: MonsterDao
    ): CollectionRepository {
        return CollectionRepositoryImpl(localDataSource, monsterDao)
    }

    @Provides
    fun provideGetCollectionUseCase(
        repository: CollectionRepository
    ): GetCollectionUseCase {
        return GetCollectionUseCase(repository)
    }
}