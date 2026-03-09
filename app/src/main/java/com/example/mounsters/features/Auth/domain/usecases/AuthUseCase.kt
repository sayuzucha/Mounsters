package com.example.mounsters.features.Auth.domain.usecases

import com.example.mounsters.features.Auth.domain.entities.LoginRequest
import com.example.mounsters.features.Auth.domain.entities.RegisterRequest
import com.example.mounsters.features.Auth.domain.entities.User
import com.example.mounsters.features.Auth.domain.repositories.AuthRepository


class AuthUseCase(
    private val repository: AuthRepository
) {

    suspend fun register(registerRequest: RegisterRequest): Result<User> {
        return try {
            val user = repository.register(registerRequest)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(loginRequest: LoginRequest): Result<User> {
        return try {
            val user = repository.login(loginRequest)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}