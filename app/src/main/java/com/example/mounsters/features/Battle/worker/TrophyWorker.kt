package com.example.mounsters.features.Battle.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.mounsters.core.network.ApiService
import com.example.mounsters.features.Collection.data.datasources.remote.models.LevelUpRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class TrophyWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val apiService: ApiService
) : CoroutineWorker(context, params) {

    companion object {
        const val KEY_MONSTER_ID    = "monster_id"
        const val KEY_MONSTER_NAME  = "monster_name"
        const val KEY_TROPHY_VALUE  = "trophy_value"

        fun buildRequest(
            monsterId: String,
            monsterName: String,
            trophyValue: Int
        ): OneTimeWorkRequest {
            val data = workDataOf(
                KEY_MONSTER_ID   to monsterId,
                KEY_MONSTER_NAME to monsterName,
                KEY_TROPHY_VALUE to trophyValue
            )
            return OneTimeWorkRequestBuilder<TrophyWorker>()
                .setInputData(data)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiresBatteryNotLow(true)
                        .build()
                )
                .build()
        }
    }

    override suspend fun doWork(): Result {
        val monsterId   = inputData.getString(KEY_MONSTER_ID)   ?: return Result.failure()
        val monsterName = inputData.getString(KEY_MONSTER_NAME) ?: return Result.failure()
        val trophyValue = inputData.getInt(KEY_TROPHY_VALUE, 1)

        // Simula el recorrido al trofeo
        delay(3000)

        // Llama al endpoint de la API
        return try {
            apiService.levelUpMonster(
                id   = monsterId,
                body = LevelUpRequest(trophy_value = trophyValue)
            )
            showNotification(monsterName, trophyValue)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification(monsterName: String, trophyValue: Int) {
        val channelId = "battle_channel"
        val manager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "Batallas",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply { description = "Resultados de batallas" }
            )
        }

        manager.notify(
            monsterName.hashCode(),
            NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("¡$monsterName subió de nivel!")
                .setContentText("Recogió un trofeo y ganó +$trophyValue niveles 🏆")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
        )
    }
}