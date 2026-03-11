package com.example.mounsters.core.database.dao

import androidx.room.*
import com.example.mounsters.core.database.entities.SpawnEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpawnDao {

    // GET /spawns — spawns activos en el mapa
    @Query("SELECT * FROM spawns WHERE active = 1")
    fun getActiveSpawns(): Flow<List<SpawnEntity>>

    // GET /spawns/:id
    @Query("SELECT * FROM spawns WHERE id = :id")
    suspend fun getSpawnById(id: String): SpawnEntity?

    // POST /spawns — cuando el WS emite un spawn nuevo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpawn(spawn: SpawnEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpawns(spawns: List<SpawnEntity>)

    // PUT /spawns/:id — desactivar tras captura
    @Query("UPDATE spawns SET active = 0 WHERE id = :id")
    suspend fun deactivateSpawn(id: String)

    // Limpiar spawns expirados localmente
    @Query("DELETE FROM spawns WHERE expires_at < :now")
    suspend fun deleteExpiredSpawns(now: String)

    @Delete
    suspend fun deleteSpawn(spawn: SpawnEntity)

    @Query("DELETE FROM spawns")
    suspend fun deleteAll()
}