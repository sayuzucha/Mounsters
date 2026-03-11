package com.example.mounsters.core.di

import android.content.Context
import com.example.mounsters.BuildConfig

import com.example.mounsters.core.network.ApiService
import com.example.mounsters.core.network.AuthInterceptor
import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.Auth.data.repositories.AuthRepositoryImpl
import com.example.mounsters.features.Auth.domain.repositories.AuthRepository


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    val tokenManager = TokenManager(context)

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(tokenManager))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(apiService, tokenManager)
    }
}