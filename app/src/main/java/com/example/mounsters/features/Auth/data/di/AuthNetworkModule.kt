package com.example.mounsters.features.Auth.data.di

import com.example.mounsters.features.Auth.data.datasources.remote.api.AuthApiService
import com.example.mounsters.core.di.MonsterApiRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {

    @Provides
    @Singleton
    fun provideAuthApiService(
        @MonsterApiRetrofit retrofit: Retrofit // 🔹 usa la misma anotación que tu NetworkModule
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}