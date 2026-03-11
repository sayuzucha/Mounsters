package com.example.mounsters.core.di

import android.content.Context
import androidx.room.Room
import com.example.mounsters.core.database.AppDatabase
import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.database.dao.MonsterDao
import com.example.mounsters.core.database.dao.NotificationDao
import com.example.mounsters.core.database.dao.SpawnDao
import com.example.mounsters.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "monster-hunt.db"
        ).build()
    }

    // Proveemos los DAOs individualmente
    // Esto permite que una Feature pida solo el DAO que le interesa
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideMonsterDao(db: AppDatabase): MonsterDao = db.monsterDao()

    @Provides
    fun provideCaptureDao(db: AppDatabase): CaptureDao = db.captureDao()

    @Provides
    fun provideSpawnDao(db: AppDatabase): SpawnDao = db.spawnDao()

    @Provides
    fun provideNotificationDao(db: AppDatabase): NotificationDao = db.notificationDao()
}