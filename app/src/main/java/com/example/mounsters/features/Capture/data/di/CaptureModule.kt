package com.example.mounsters.features.Capture.data.di

import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.database.dao.MonsterDao
import com.example.mounsters.core.di.MonsterApiRetrofit
import com.example.mounsters.features.Capture.data.datasources.remote.api.CaptureApiService
import com.example.mounsters.features.Capture.data.repository.CaptureRepositoryImpl
import com.example.mounsters.features.Capture.domain.repository.CaptureRepository
import com.example.mounsters.features.Capture.domain.usecases.CaptureMonsterUseCase
import com.example.mounsters.features.Capture.domain.usecases.SaveCaptureToRoomUseCase
import com.example.mounsters.features.mounsters.data.datasources.remote.api.MonstersApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CaptureModule {

    @Provides
    @Singleton
    fun provideCaptureApiService(
        @MonsterApiRetrofit retrofit: Retrofit  // ← usa el mismo Retrofit que ya tiene AuthInterceptor
    ): CaptureApiService {
        return retrofit.create(CaptureApiService::class.java)
    }

    @Provides
    fun provideSaveCaptureToRoomUseCase(
        captureDao: CaptureDao,
        monsterDao: MonsterDao,
        @MonsterApiRetrofit retrofit: Retrofit
    ): SaveCaptureToRoomUseCase {
        val monstersApiService = retrofit.create(MonstersApiService::class.java)
        return SaveCaptureToRoomUseCase(captureDao, monsterDao, monstersApiService)
    }

    @Provides
    @Singleton
    fun provideCaptureRepository(
        api: CaptureApiService
    ): CaptureRepository {
        return CaptureRepositoryImpl(api)
    }

    @Provides
    fun provideCaptureMonsterUseCase(
        repository: CaptureRepository
    ): CaptureMonsterUseCase {
        return CaptureMonsterUseCase(repository)
    }
}