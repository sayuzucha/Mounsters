package com.example.mounsters.features.Auth.data.di

import com.example.mounsters.core.di.AppContainer
import com.example.mounsters.features.Auth.domain.usecases.AuthUseCase
import com.example.mounsters.features.auth.domain.usecases.AuthUseCase
import com.example.mounsters.features.auth.presentation.viewmodels.AuthViewModelFactory

class AuthModule(
    private val appContainer: AppContainer
) {

    private fun provideAuthUseCase() = AuthUseCase(appContainer.authRepository)

    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory(
            authUseCase = provideAuthUseCase()
        )
    }
}