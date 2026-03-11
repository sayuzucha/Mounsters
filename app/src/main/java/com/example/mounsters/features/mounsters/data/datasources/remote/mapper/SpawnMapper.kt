package com.example.mounsters.features.mounsters.data.datasources.remote.mapper

import com.example.mounsters.features.mounsters.data.datasources.remote.models.Spawn as SpawnDto
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.domain.entities.Monster

fun SpawnDto.toDomain(): Spawn {
    return Spawn(
        spawnId = spawnId ?: id ?: "",
        monster = Monster(
            id = monster.id ?: "",
            name = monster.name,
            type = monster.type ?: "",
            rarity = monster.rarity ?: "",
            baseHp = 0,          // Como API nearby no da estos campos, ponemos 0
            baseAttack = 0,      // igual
            habitat = "",        // igual
            imageUrl = "",       // igual
            description = ""     // igual
        ),
        lat = lat,
        lng = lng,
        expiresAt = expiresAt ?: ""
    )
}