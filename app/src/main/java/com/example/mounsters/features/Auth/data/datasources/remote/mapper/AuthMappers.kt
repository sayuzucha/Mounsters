package com.example.mounsters.features.Auth.data.datasources.remote.mapper

import com.example.mounsters.features.Auth.data.datasources.remote.models.LoginResponse
import com.example.mounsters.features.Auth.data.datasources.remote.models.RegisterResponse
import com.example.mounsters.features.Auth.domain.entities.User


fun RegisterResponse.toDomain(): User {
    return User(
        id = user.id,
        username = user.username,
        email = user.email,
        level = user.level,
        xp = user.xp,
        createdAt = user.createdAt,
        token = token
    )
}

fun LoginResponse.toDomain(): User {
    return User(
        id = user.id,
        username = user.username,
        email = user.email,
        level = user.level,
        xp = user.xp,
        createdAt = null,
        token = token
    )
}