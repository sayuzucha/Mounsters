package com.example.mounsters.features.Auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mounsters.features.Auth.domain.entities.LoginRequest
import com.example.mounsters.features.Auth.domain.entities.RegisterRequest
import com.example.mounsters.features.Auth.domain.usecases.AuthUseCase
import com.example.mounsters.features.Auth.presentation.screens.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _uiState = kotlinx.coroutines.flow.MutableStateFlow(AuthUiState())
    val uiState: kotlinx.coroutines.flow.StateFlow<AuthUiState> = _uiState

    fun register(registerRequest: RegisterRequest) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val result = authUseCase.register(registerRequest)
                _uiState.value = result.fold(
                    onSuccess = { user -> AuthUiState(user = user) },
                    onFailure = { e -> AuthUiState(error = e.message) }
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(error = e.message)
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val result = authUseCase.login(loginRequest)
                _uiState.value = result.fold(
                    onSuccess = { user -> AuthUiState(user = user) },
                    onFailure = { e -> AuthUiState(error = e.message) }
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState(error = e.message)
            }
        }
    }
}