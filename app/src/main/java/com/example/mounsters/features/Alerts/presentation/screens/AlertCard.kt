package com.example.mounsters.features.Alerts.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mounsters.features.Alerts.domain.entities.AlertItem
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AlertCard(
    alert: AlertItem,
    onClick: () -> Unit
) {
    val bgColor = if (alert.isRead)
        Color(0xFF0F172A) else Color(0xFF1E293B)

    val borderColor = if (alert.isRead)
        Color(0xFF1E293B) else Color(0xFF00E5FF).copy(alpha = 0.4f)

    val icon = when (alert.type) {
        "SPAWN"    -> "🐉"
        "CAPTURE"  -> "⚡"
        "LEVEL_UP" -> "🏆"
        else       -> "🔔"
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 32.sp)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alert.title,
                    color = Color.White,
                    fontWeight = if (!alert.isRead) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                )
                Text(
                    text = alert.body,
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
                Text(
                    text = formatDate(alert.createdAt),
                    color = Color(0xFF475569),
                    fontSize = 11.sp
                )
            }
            if (!alert.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00E5FF))
                )
            }
        }
    }
}

fun formatDate(dateStr: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(dateStr)
        val out = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        out.format(date ?: Date())
    } catch (e: Exception) { dateStr }
}