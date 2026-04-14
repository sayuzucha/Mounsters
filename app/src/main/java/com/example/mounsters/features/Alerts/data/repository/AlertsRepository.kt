package com.example.mounsters.features.Alerts.data.repository

import com.example.mounsters.core.network.ApiService
import com.example.mounsters.features.Alerts.domain.entities.AlertItem
import javax.inject.Inject

class AlertsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAlerts(): List<AlertItem> {
        val response = apiService.getAlerts()
        return response.data.map { dto ->
            AlertItem(
                id          = dto.id,
                title       = dto.title,
                body        = dto.body,
                type        = dto.type,
                monsterName = dto.monsterName ?: "",
                isRead      = dto.isRead == 1,
                createdAt   = dto.createdAt
            )
        }
    }

    suspend fun getUnreadCount(): Int {
        return apiService.getUnreadCount().count
    }

    suspend fun markAsRead(id: String) {
        apiService.markAlertAsRead(id)
    }

    suspend fun markAllAsRead() {
        apiService.markAllAlertsRead()
    }
}