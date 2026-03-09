package com.example.mounsters.features.Auth.domain.entities


data class User(
    val id: String,
    val username: String,
    val email: String,
    val level: Int = 1,
    val xp: Int = 0,
    val createdAt: String? = null,
    val token: String? = null
)