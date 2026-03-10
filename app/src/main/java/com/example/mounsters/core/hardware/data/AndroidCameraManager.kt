package com.example.mounsters.core.hardware.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import com.example.mounsters.core.hardware.domain.CameraManager
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject

class AndroidCameraManager @Inject constructor(
    @ApplicationContext private val context: Context
) : CameraManager {

    override fun hasCamera(): Boolean {
        return context.packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_ANY
        )
    }

    override fun openCamera(): Intent? {
        if (!hasCamera()) return null

        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}