package com.example.mounsters.features.Auth.domain.repositories

import com.example.mounsters.features.Auth.domain.entities.LoginRequest
import com.example.mounsters.features.Auth.domain.entities.RegisterRequest
import com.example.mounsters.features.Auth.domain.entities.User

interface AuthRepository {

    suspend fun register(
        registerRequest: RegisterRequest
    ): User

    suspend fun login(
        loginRequest: LoginRequest
    ): User
}