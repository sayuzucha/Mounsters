package com.example.mounsters.features.mounsters.presentation.components

import androidx.compose.runtime.*
import androidx.compose.material3.Text
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.Duration

@Composable
fun SpawnTimer(
    expiresAt: String
) {

    var timeLeft by remember { mutableStateOf("") }

    LaunchedEffect(expiresAt) {

        while (true) {

            try {

                val now = Instant.now()
                val expire = Instant.parse(expiresAt)

                val duration = Duration.between(now, expire)

                val minutes = duration.toMinutes()
                val seconds = duration.seconds % 60

                timeLeft = "$minutes:${seconds.toString().padStart(2,'0')}"

            } catch (e: Exception) {

                timeLeft = "--:--"

            }

            delay(1000)
        }
    }

    Text(text = "Despawns in: $timeLeft")
}