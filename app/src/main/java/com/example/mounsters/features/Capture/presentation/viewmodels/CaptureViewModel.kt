package com.example.mounsters.features.Capture.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mounsters.core.hardware.domain.AccelerometerManager
import com.example.mounsters.core.hardware.domain.VibrateManager
import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.Capture.data.datasources.remote.models.CaptureRequest
import com.example.mounsters.features.Capture.domain.usecases.CaptureMonsterUseCase
import com.example.mounsters.features.Capture.domain.usecases.SaveCaptureToRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CaptureUiState(
    val isLoading: Boolean = false,
    val captured: Boolean = false,
    val failed: Boolean = false,
    val error: String? = null,
    val ballThrown: Boolean = false
)

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val captureMonsterUseCase: CaptureMonsterUseCase,
    private val saveCaptureToRoomUseCase: SaveCaptureToRoomUseCase,
    private val accelerometerManager: AccelerometerManager,
    private val vibrateManager: VibrateManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CaptureUiState())
    val uiState: StateFlow<CaptureUiState> = _uiState.asStateFlow()

    fun startListening(onThrow: () -> Unit) {
        accelerometerManager.startListening {
            _uiState.value = _uiState.value.copy(ballThrown = true)
            accelerometerManager.stopListening()
            onThrow()
        }
    }

    fun stopListening() {
        accelerometerManager.stopListening()
    }

    fun captureMonster(
        spawnId: String,
        monsterId: String,
        lat: Double,
        lng: Double,
        nickname: String
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val result = captureMonsterUseCase(
                CaptureRequest(
                    monsterId = monsterId,
                    spawnId   = spawnId,
                    lat       = lat,
                    lng       = lng,
                    nickname  = nickname
                )
            )

            result.fold(
                onSuccess = { response ->
                    // Guardar en Room
                    val userId = tokenManager.getToken() ?: "local_user"
                    saveCaptureToRoomUseCase(response, userId)

                    vibrateManager.vibrate(800)
                    _uiState.value = CaptureUiState(captured = true)
                },
                onFailure = { e ->
                    vibrateManager.vibrate(200)
                    _uiState.value = CaptureUiState(
                        failed = true,
                        error  = e.message
                    )
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        accelerometerManager.stopListening()
    }
}