package com.example.mounsters.features.mounsters.data.di

import com.example.mounsters.features.mounsters.data.datasources.remote.api.MonstersApiService
import com.example.mounsters.core.di.MonsterApiRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MonstersNetworkModule {

    @Provides
    @Singleton
    fun provideMonstersApiService(
        @MonsterApiRetrofit retrofit: Retrofit // 🔹 aquí agregamos el qualifier
    ): MonstersApiService {
        return retrofit.create(MonstersApiService::class.java)
    }
}