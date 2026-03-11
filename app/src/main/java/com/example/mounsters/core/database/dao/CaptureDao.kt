package com.example.mounsters.core.database.dao

import androidx.room.*
import com.example.mounsters.core.database.entities.CaptureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CaptureDao {

    // GET /captures — mi colección completa
    @Query("SELECT * FROM captures WHERE user_id = :userId ORDER BY captured_at DESC")
    fun getCapturesByUser(userId: String): Flow<List<CaptureEntity>>

    // GET /captures?type=fire (necesita JOIN con monsters)
    @Query("""
        SELECT c.* FROM captures c
        INNER JOIN monsters m ON c.monster_id = m.id
        WHERE c.user_id = :userId AND m.type = :type
    """)
    fun getCapturesByType(userId: String, type: String): Flow<List<CaptureEntity>>

    // GET /captures?rarity=rare
    @Query("""
        SELECT c.* FROM captures c
        INNER JOIN monsters m ON c.monster_id = m.id
        WHERE c.user_id = :userId AND m.rarity = :rarity
    """)
    fun getCapturesByRarity(userId: String, rarity: String): Flow<List<CaptureEntity>>

    // GET /captures/:id
    @Query("SELECT * FROM captures WHERE id = :id AND user_id = :userId")
    suspend fun getCaptureById(id: String, userId: String): CaptureEntity?

    // GET /captures/stats
    @Query("SELECT COUNT(*) FROM captures WHERE user_id = :userId")
    suspend fun getTotalCaptures(userId: String): Int

    @Query("SELECT COUNT(DISTINCT monster_id) FROM captures WHERE user_id = :userId")
    suspend fun getUniqueMonsters(userId: String): Int

    // POST /captures
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapture(capture: CaptureEntity)

    // PUT /captures/:id — renombrar / subir nivel
    @Update
    suspend fun updateCapture(capture: CaptureEntity)

    // DELETE /captures/:id — liberar criatura
    @Delete
    suspend fun deleteCapture(capture: CaptureEntity)

    @Query("DELETE FROM captures WHERE user_id = :userId")
    suspend fun deleteAllByUser(userId: String)
}