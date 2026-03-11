package com.example.mounsters.core.database.dao

import androidx.room.*
import com.example.mounsters.core.database.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    // GET /notifications
    @Query("SELECT * FROM notifications WHERE user_id = :userId ORDER BY created_at DESC")
    fun getNotificationsByUser(userId: String): Flow<List<NotificationEntity>>

    // GET /notifications?read=false
    @Query("SELECT * FROM notifications WHERE user_id = :userId AND read = :read ORDER BY created_at DESC")
    fun getNotificationsByRead(userId: String, read: Boolean): Flow<List<NotificationEntity>>

    // Badge — cuántas sin leer
    @Query("SELECT COUNT(*) FROM notifications WHERE user_id = :userId AND read = 0")
    fun getUnreadCount(userId: String): Flow<Int>

    // GET /notifications/:id
    @Query("SELECT * FROM notifications WHERE id = :id AND user_id = :userId")
    suspend fun getNotificationById(id: String, userId: String): NotificationEntity?

    // POST /notifications
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    // PUT /notifications/:id  { read: true }
    @Query("UPDATE notifications SET read = 1 WHERE id = :id")
    suspend fun markAsRead(id: String)

    // PUT /notifications/read-all
    @Query("UPDATE notifications SET read = 1 WHERE user_id = :userId")
    suspend fun markAllAsRead(userId: String)

    // DELETE /notifications/:id
    @Delete
    suspend fun deleteNotification(notification: NotificationEntity)

    // DELETE /notifications — limpiar todas
    @Query("DELETE FROM notifications WHERE user_id = :userId")
    suspend fun clearAllByUser(userId: String)
}