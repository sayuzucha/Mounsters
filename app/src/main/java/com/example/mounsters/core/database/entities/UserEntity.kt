package com.example.mounsters.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "avatar")
    val avatar: String?,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "xp")
    val xp: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: String
)