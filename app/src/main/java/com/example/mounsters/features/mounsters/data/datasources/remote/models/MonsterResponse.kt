package com.example.mounsters.features.mounsters.data.datasources.remote.models

data class MonsterResponse(
    val success: Boolean,
    val total: Int,
    val data: List<Monster>
)

data class Monster(
    val id: String,
    val name: String,
    val type: String,
    val rarity: String,
    val baseHp: Int,
    val baseAttack: Int,
    val habitat: String,
    val imageUrl: String,
    val description: String
)