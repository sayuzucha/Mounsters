package com.example.mounsters

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MonsterHuntMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title       = remoteMessage.notification?.title ?: remoteMessage.data["title"] ?: return
        val body        = remoteMessage.notification?.body  ?: remoteMessage.data["body"]  ?: ""
        val type        = remoteMessage.data["type"] ?: "GENERAL"
        val monsterName = remoteMessage.data["monsterName"] ?: ""

        showNotification(title, body, type)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Aquí enviarás el token a tu API
        android.util.Log.d("FCM", "Token nuevo: $token")
    }

    private fun showNotification(title: String, body: String, type: String) {
        val channelId = when (type) {
            "SPAWN"    -> "spawn_channel"
            "CAPTURE"  -> "capture_channel"
            "LEVEL_UP" -> "battle_channel"
            else       -> "general_channel"
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canales (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            listOf(
                Triple("spawn_channel",   "Llegada de Monstruos", "Alertas cuando aparece un monstruo"),
                Triple("capture_channel", "Capturas",             "Cuando alguien captura un monstruo"),
                Triple("battle_channel",  "Batallas",             "Resultados de batallas y trofeos"),
                Triple("general_channel", "General",              "Notificaciones generales")
            ).forEach { (id, name, desc) ->
                manager.createNotificationChannel(
                    NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
                        .apply { description = desc }
                )
            }
        }

        // Intent para abrir la app al tocar la notificación
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}