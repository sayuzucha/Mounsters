package com.example.mounsters.features.Collection.domain.entities

data class CapturedMonster(
    val id: String,
    val monsterId: String,
    val nickname: String,
    val level: Int,
    val xp: Int,
    val hp: Int,
    val attack: Int,
    val monsterName: String,
    val monsterType: String,
    val monsterRarity: String,
    val imageUrl: String,
    val capturedAt: String
)