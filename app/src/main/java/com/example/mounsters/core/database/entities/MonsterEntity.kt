package com.example.mounsters.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monsters")
data class MonsterEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "rarity")
    val rarity: String,

    @ColumnInfo(name = "base_hp")
    val baseHp: Int,

    @ColumnInfo(name = "base_attack")
    val baseAttack: Int,

    @ColumnInfo(name = "habitat")
    val habitat: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "description")
    val description: String?
)