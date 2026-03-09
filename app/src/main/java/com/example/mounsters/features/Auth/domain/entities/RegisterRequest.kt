package com.example.mounsters.features.Auth.domain.entities


data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)