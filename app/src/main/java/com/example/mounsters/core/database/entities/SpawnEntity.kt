package com.example.mounsters.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "spawns",
    foreignKeys = [ForeignKey(
        entity = MonsterEntity::class,
        parentColumns = ["id"],
        childColumns = ["monster_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SpawnEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "monster_id", index = true)
    val monsterId: String,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lng")
    val lng: Double,

    @ColumnInfo(name = "active")
    val active: Boolean,

    @ColumnInfo(name = "spawned_at")
    val spawnedAt: String,

    @ColumnInfo(name = "expires_at")
    val expiresAt: String
)