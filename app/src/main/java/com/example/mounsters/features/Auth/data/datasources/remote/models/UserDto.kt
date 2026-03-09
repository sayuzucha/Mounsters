package com.example.mounsters.features.Auth.data.datasources.remote.models

data class UserDto(
    val id: String,
    val username: String,
    val email: String,
    val level: Int,
    val xp: Int,
    val createdAt: String? = null
)