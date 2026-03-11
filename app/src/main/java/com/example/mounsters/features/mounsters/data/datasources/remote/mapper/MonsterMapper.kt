package com.example.mounsters.features.mounsters.data.datasources.remote.mapper

import com.example.mounsters.features.mounsters.data.datasources.remote.models.MonsterDto
import com.example.mounsters.features.mounsters.domain.entities.Monster

fun MonsterDto.toDomain(): Monster {
    return Monster(
        id = id,
        name = name,
        type = type,
        rarity = rarity,
        baseHp = baseHp,
        baseAttack = baseAttack,
        habitat = habitat,
        imageUrl = imageUrl,
        description = description
    )
}