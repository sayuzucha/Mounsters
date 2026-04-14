package com.example.mounsters.features.Alerts.domain.entities

data class AlertItem(
    val id: String,
    val title: String,
    val body: String,
    val type: String,
    val monsterName: String,
    val isRead: Boolean,
    val createdAt: String
)