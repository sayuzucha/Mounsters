package com.example.mounsters.features.mounsters.data.di

import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.mounsters.data.repositories.MonsterRepositoryImpl
import com.example.mounsters.features.mounsters.data.datasources.remote.api.MonstersApiService
import com.example.mounsters.features.mounsters.domain.repositories.MonsterRepository
import com.example.mounsters.features.mounsters.domain.usecases.GetNearbySpawnsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MonsterUseCaseModule {

    // -------------------
    // Repository
    // -------------------
    @Provides
    @Singleton
    fun provideMonsterRepository(
        api: MonstersApiService,
        tokenManager: TokenManager
    ): MonsterRepository {
        return MonsterRepositoryImpl(api, tokenManager)
    }

    // -------------------
    // UseCase
    // -------------------
    @Provides
    @Singleton
    fun provideGetNearbySpawnsUseCase(repository: MonsterRepository): GetNearbySpawnsUseCase {
        return GetNearbySpawnsUseCase(repository)
    }
}