package com.example.mounsters.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "captures",
    foreignKeys = [ForeignKey(
        entity = MonsterEntity::class,
        parentColumns = ["id"],
        childColumns = ["monster_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CaptureEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "monster_id", index = true)
    val monsterId: String,

    @ColumnInfo(name = "spawn_id")
    val spawnId: String?,

    @ColumnInfo(name = "nickname")
    val nickname: String,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "xp")
    val xp: Int,

    @ColumnInfo(name = "hp")
    val hp: Int,

    @ColumnInfo(name = "attack")
    val attack: Int,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lng")
    val lng: Double,

    @ColumnInfo(name = "captured_at")
    val capturedAt: String
)