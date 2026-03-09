package com.example.mounsters.features.Auth.data.repositories

import com.example.mounsters.core.network.ApiService
import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.Auth.domain.entities.LoginRequest
import com.example.mounsters.features.Auth.domain.entities.RegisterRequest
import com.example.mounsters.features.Auth.domain.entities.User
import com.example.mounsters.features.Auth.domain.repositories.AuthRepository
import com.example.mounsters.features.auth.data.datasources.remote.mapper.toDomain


class AuthRepositoryImpl(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun register(registerRequest: RegisterRequest): User {
        val response = api.register(registerRequest)

        tokenManager.saveToken(response.token)

        return response.toDomain()
    }

    override suspend fun login(loginRequest: LoginRequest): User {
        val response = api.login(loginRequest)

        tokenManager.saveToken(response.token)

        return response.toDomain()
    }
}