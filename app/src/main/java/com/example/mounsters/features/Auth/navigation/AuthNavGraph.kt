package com.example.mounsters.features.Auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mounsters.core.navigation.FeatureNavGraph
import com.example.mounsters.core.navigation.NavigationRoutes
import com.example.mounsters.features.Auth.presentation.screens.AuthScreen
import com.example.mounsters.features.Auth.presentation.screens.LoginScreen
import com.example.mounsters.features.Auth.presentation.screens.SplashScreen

class AuthNavGraph : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {

        // SPLASH
        navGraphBuilder.composable(NavigationRoutes.SPLASH) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(NavigationRoutes.LOGIN) {
                        popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // LOGIN
        navGraphBuilder.composable(NavigationRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavigationRoutes.AUTH)  // ← esta ruta debe existir
                }
            )
        }

        // REGISTRO
        navGraphBuilder.composable(NavigationRoutes.AUTH) {
            AuthScreen(
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }
}