package com.example.mounsters.core.di

import com.example.mounsters.BuildConfig
import com.example.mounsters.core.network.ApiService
import com.example.mounsters.core.network.AuthInterceptor
import com.example.mounsters.core.util.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @MonsterApiRetrofit
    fun provideRetrofit(tokenManager: TokenManager): Retrofit {  // ← solo UNA vez
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        @MonsterApiRetrofit retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}