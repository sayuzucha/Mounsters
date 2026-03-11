package com.example.mounsters.features.mounsters.data.datasources.remote.models

import com.example.mounsters.features.mounsters.domain.entities.Monster

data class MonsterResponse(
    val success: Boolean,
    val total: Int,
    val data: List<Monster>
)
