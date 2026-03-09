package com.example.mounsters.features.Auth.data.datasources.remote.models

data class RegisterResponse(
    val success: Boolean,
    val token: String,
    val user: UserDto
)

data class LoginResponse(
    val success: Boolean,
    val token: String,
    val user: UserDto
)