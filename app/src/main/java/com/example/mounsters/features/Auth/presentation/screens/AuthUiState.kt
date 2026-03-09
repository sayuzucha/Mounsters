package com.example.mounsters.features.Auth.presentation.screens

import com.example.mounsters.features.Auth.domain.entities.User


data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)