package com.example.mounsters.features.Auth.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mounsters.features.Auth.domain.usecases.AuthUseCase


class AuthViewModelFactory(
    private val authUseCase: AuthUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(authUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}