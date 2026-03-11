package com.example.mounsters.core.database.dao

import androidx.room.*
import com.example.mounsters.core.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // GET /auth/me — sesión activa
    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getLoggedUser(): UserEntity?

    // POST /auth/register o login — guardar sesión local
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    // PUT /auth/me
    @Update
    suspend fun updateUser(user: UserEntity)

    // DELETE /auth/me
    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}