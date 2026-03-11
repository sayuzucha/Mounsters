package com.example.mounsters.features.Notifications.data.datasources.remote.models

data class NotificationResponse(
    val success: Boolean,
    val total: Int,
    val unread: Int,
    val data: List<Notification>
)

data class Notification(
    val id: String,
    val type: String,
    val title: String,
    val body: String,
    val read: Boolean,
    val createdAt: String
)