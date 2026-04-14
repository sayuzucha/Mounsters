package com.example.mounsters.features.Alerts.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class AlertResponse(
    val success: Boolean,
    val data: List<AlertDto>
)

data class AlertDto(
    val id: String,
    val title: String,
    val body: String,
    val type: String,
    @SerializedName("monster_name") val monsterName: String?,
    @SerializedName("is_read") val isRead: Int,
    @SerializedName("created_at") val createdAt: String
)

data class UnreadCountResponse(
    val success: Boolean,
    val count: Int
)

data class CreateAlertRequest(
    val title: String,
    val body: String,
    val type: String,
    @SerializedName("monster_name") val monsterName: String
)