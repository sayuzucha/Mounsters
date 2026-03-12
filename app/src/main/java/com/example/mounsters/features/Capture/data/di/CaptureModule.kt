package com.example.mounsters.features.capture.di

import com.example.mounsters.features.capture.data.repository.CaptureRepositoryImpl
import com.example.mounsters.features.capture.domain.repository.CaptureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CaptureModule {

    @Binds
    abstract fun bindCaptureRepository(
        impl: CaptureRepositoryImpl
    ): CaptureRepository

}