package com.example.mounsters.core.database.dao

import androidx.room.*
import com.example.mounsters.core.database.entities.MonsterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonsterDao {

    @Query("SELECT * FROM monsters")
    fun getAllMonsters(): Flow<List<MonsterEntity>>

    @Query("SELECT * FROM monsters WHERE id = :id")
    suspend fun getMonsterById(id: String): MonsterEntity?

    // GET /monsters?type=fire
    @Query("SELECT * FROM monsters WHERE type = :type")
    fun getMonstersByType(type: String): Flow<List<MonsterEntity>>

    // GET /monsters?rarity=legendary
    @Query("SELECT * FROM monsters WHERE rarity = :rarity")
    fun getMonstersByRarity(rarity: String): Flow<List<MonsterEntity>>

    // GET /monsters?search=flame
    @Query("SELECT * FROM monsters WHERE name LIKE '%' || :search || '%'")
    fun searchMonsters(search: String): Flow<List<MonsterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonsters(monsters: List<MonsterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonster(monster: MonsterEntity)

    @Update
    suspend fun updateMonster(monster: MonsterEntity)

    @Delete
    suspend fun deleteMonster(monster: MonsterEntity)

    @Query("DELETE FROM monsters")
    suspend fun deleteAll()
}