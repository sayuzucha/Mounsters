package com.example.mounsters.core.hardware.domain

interface AccelerometerManager {

    fun startListening(onThrowDetected: () -> Unit)

    fun stopListening()
}