package com.example.mounsters.core.hardware.domain



interface VibrateManager {
    fun vibrate(durationMillis: Long = 200)
    fun hasVibrator(): Boolean
}