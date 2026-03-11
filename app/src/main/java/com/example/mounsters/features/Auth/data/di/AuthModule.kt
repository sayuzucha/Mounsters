package com.example.mounsters.features.Auth.data.di

import com.example.mounsters.features.Auth.domain.repositories.AuthRepository
import com.example.mounsters.features.Auth.domain.usecases.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {

    @Provides
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCase {
        return AuthUseCase(repository)
    }
}