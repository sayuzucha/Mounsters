package com.example.mounsters.core.hardware.domain

import android.content.Intent

interface CameraManager {

    fun hasCamera(): Boolean

    fun openCamera(): Intent?
}