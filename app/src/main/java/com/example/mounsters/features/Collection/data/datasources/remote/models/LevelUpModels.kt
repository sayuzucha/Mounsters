package com.example.mounsters.features.Collection.data.datasources.remote.models

data class LevelUpRequest(
    val trophy_value: Int
)

data class LevelUpResponse(
    val success: Boolean,
    val trophy_value: Int,
    val data: MonsterLevelData
)

data class MonsterLevelData(
    val id: String,
    val name: String,
    val level: Int
)