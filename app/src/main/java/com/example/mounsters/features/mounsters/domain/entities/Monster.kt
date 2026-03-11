package com.example.mounsters.features.mounsters.domain.entities

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