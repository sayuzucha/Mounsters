package com.example.mounsters.core.hardware.data


import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import com.example.mounsters.core.hardware.domain.VibrateManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidVibrateManager @Inject constructor(
    @ApplicationContext private val context: Context
) : VibrateManager {

    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    override fun hasVibrator(): Boolean = vibrator.hasVibrator()

    @RequiresPermission(Manifest.permission.VIBRATE)
    override fun vibrate(durationMillis: Long) {
        if (!hasVibrator()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    durationMillis,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(durationMillis)
        }
    }
}