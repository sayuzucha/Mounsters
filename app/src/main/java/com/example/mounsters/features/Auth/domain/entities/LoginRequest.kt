package com.example.mounsters.features.Auth.domain.entities

data class LoginRequest(
    val email: String,
    val password: String
)