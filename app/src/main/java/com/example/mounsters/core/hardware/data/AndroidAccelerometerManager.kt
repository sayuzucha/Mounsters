package com.example.mounsters.core.hardware.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.mounsters.core.hardware.domain.AccelerometerManager
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject
import kotlin.math.sqrt

class AndroidAccelerometerManager @Inject constructor(
    @ApplicationContext context: Context
) : AccelerometerManager, SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var onThrow: (() -> Unit)? = null

    override fun startListening(onThrowDetected: () -> Unit) {
        onThrow = onThrowDetected
        sensorManager.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val acceleration = sqrt(x * x + y * y + z * z)

        if (acceleration > 18) { // umbral de lanzamiento
            onThrow?.invoke()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}