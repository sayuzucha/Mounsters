package com.example.mounsters.features.Auth.data.datasources.remote.api

import com.example.mounsters.features.Auth.data.datasources.remote.models.LoginResponse
import com.example.mounsters.features.Auth.data.datasources.remote.models.ProfileResponse
import com.example.mounsters.features.Auth.data.datasources.remote.models.RegisterResponse
import com.example.mounsters.features.Auth.data.datasources.remote.models.UserDto
import com.example.mounsters.features.Auth.domain.entities.LoginRequest
import com.example.mounsters.features.Auth.domain.entities.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("auth/me")
    suspend fun getProfile(): ProfileResponse

    @PUT("auth/me")
    suspend fun updateProfile(
        @Body user: UserDto
    ): UserDto

    @DELETE("auth/me")
    suspend fun deleteAccount(): Map<String, Any>
}