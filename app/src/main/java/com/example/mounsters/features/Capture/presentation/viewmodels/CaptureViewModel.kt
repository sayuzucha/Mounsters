package com.example.mounsters.features.capture.presentation.viewmodels

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mounsters.core.hardware.domain.AccelerometerManager
import com.example.mounsters.core.hardware.domain.CameraManager
import com.example.mounsters.features.capture.domain.usecases.CaptureMonsterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val accelerometerManager: AccelerometerManager,
    private val cameraManager: CameraManager,
    private val captureMonsterUseCase: CaptureMonsterUseCase
) : ViewModel() {

    // ===============================
    // UI STATES
    // ===============================

    private val _captureSuccess = MutableStateFlow(false)
    val captureSuccess: StateFlow<Boolean> = _captureSuccess.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // ===============================
    // MONSTER DATA
    // ===============================

    private var spawnId: String? = null
    private var monsterId: String? = null

    fun setMonster(spawnId: String, monsterId: String) {
        this.spawnId = spawnId
        this.monsterId = monsterId
    }

    // ===============================
    // CAMERA
    // ===============================

    fun openCamera(): Intent? {
        return cameraManager.openCamera()
    }

    fun hasCamera(): Boolean {
        return cameraManager.hasCamera()
    }

    // ===============================
    // ACCELEROMETER
    // ===============================

    fun startListeningForThrow() {

        accelerometerManager.startListening {

            // cuando detecta sacudida
            throwBall()

        }

    }

    fun stopListening() {
        accelerometerManager.stopListening()
    }

    // ===============================
    // THROW BALL
    // ===============================

    private fun throwBall() {

        if (_loading.value) return

        viewModelScope.launch {

            val spawn = spawnId ?: return@launch
            val monster = monsterId ?: return@launch

            _loading.value = true
            _error.value = null

            try {

                captureMonsterUseCase(
                    spawnId = spawn,
                    monsterId = monster
                )

                _captureSuccess.value = true

            } catch (e: Exception) {

                e.printStackTrace()
                _error.value = "Capture failed"

            } finally {

                _loading.value = false

            }

        }

    }

    // ===============================
    // RESET STATE
    // ===============================

    fun resetCaptureState() {
        _captureSuccess.value = false
        _error.value = null
    }

    // ===============================
    // CLEANUP
    // ===============================

    override fun onCleared() {
        super.onCleared()
        accelerometerManager.stopListening()
    }

}