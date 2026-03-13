package com.example.mounsters.features.Auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mounsters.core.database.dao.CaptureDao
import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.Auth.data.datasources.remote.api.AuthApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = true,
    val username: String = "",
    val email: String = "",
    val level: Int = 1,
    val xp: Int = 0,
    val totalCaptured: Int = 0,
    val loggedOut: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authApiService: AuthApiService,
    private val captureDao: CaptureDao,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                val response = authApiService.getProfile()
                val user = response.user

                val userId = tokenManager.getToken() ?: "local_user"
                val totalCaptured = captureDao.getTotalCaptures(userId)

                _uiState.value = ProfileUiState(
                    isLoading     = false,
                    username      = user.username ?: "Sin nombre",
                    email         = user.email ?: "Sin email",
                    level         = user.level ?: 1,
                    xp            = user.xp ?: 0,
                    totalCaptured = totalCaptured
                )
            } catch (e: Exception) {
                android.util.Log.e("ProfileVM", "Error: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error     = e.message
                )
            }
        }
    }

    fun logout() {
        tokenManager.clearToken()
        _uiState.value = _uiState.value.copy(loggedOut = true)
    }
}
