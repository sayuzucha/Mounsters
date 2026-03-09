package com.example.mounsters.core.hardware.di


import com.example.mounsters.core.hardware.data.AndroidAccelerometerManager
import com.example.mounsters.core.hardware.data.AndroidCameraManager
import com.example.mounsters.core.hardware.data.AndroidLocationManager
import com.example.mounsters.core.hardware.domain.AccelerometerManager
import com.example.mounsters.core.hardware.domain.CameraManager
import com.example.mounsters.core.hardware.domain.LocationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {

    @Binds
    @Singleton
    abstract fun bindLocationManager(
        impl: AndroidLocationManager
    ): LocationManager

    @Binds
    @Singleton
    abstract fun bindCameraManager(
        impl: AndroidCameraManager
    ): CameraManager

    @Binds
    @Singleton
    abstract fun bindAccelerometerManager(
        impl: AndroidAccelerometerManager
    ): AccelerometerManager
}