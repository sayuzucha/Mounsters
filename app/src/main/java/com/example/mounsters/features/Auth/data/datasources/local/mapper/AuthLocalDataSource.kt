package com.example.mounsters.features.Auth.data.datasources.local.mapper

import com.example.mounsters.core.database.entities.UserEntity
import com.example.mounsters.features.Auth.domain.entities.User

// Mapper: De Local (Room) a Dominio
fun UserEntity.toDomain() = User(
    id        = id,
    username  = username,
    email     = email,
    level     = level,
    xp        = xp,
    createdAt = createdAt
)

// Mapper: De Dominio a Local (Room)
fun User.toEntity() = UserEntity(
    id        = id,
    username  = username,
    email     = email,
    avatar    = null,
    level     = level,
    xp        = xp,
    createdAt = createdAt ?: ""
)