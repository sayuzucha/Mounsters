package com.example.mounsters.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.database.dao.MonsterDao
import com.example.mounsters.core.database.dao.NotificationDao
import com.example.mounsters.core.database.dao.SpawnDao
import com.example.mounsters.core.database.dao.UserDao
import com.example.mounsters.core.database.entities.CaptureEntity
import com.example.mounsters.core.database.entities.MonsterEntity
import com.example.mounsters.core.database.entities.NotificationEntity
import com.example.mounsters.core.database.entities.SpawnEntity
import com.example.mounsters.core.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,         // tabla: users
        MonsterEntity::class,      // tabla: monsters
        CaptureEntity::class,      // tabla: captures
        SpawnEntity::class,        // tabla: spawns
        NotificationEntity::class, // tabla: notifications
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun monsterDao(): MonsterDao
    abstract fun captureDao(): CaptureDao
    abstract fun spawnDao(): SpawnDao
    abstract fun notificationDao(): NotificationDao
}