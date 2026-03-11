package com.example.mounsters.features.Auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mounsters.core.navigation.FeatureNavGraph
import com.example.mounsters.core.navigation.NavigationRoutes
import com.example.mounsters.features.Auth.presentation.screens.AuthScreen
import com.example.mounsters.features.Auth.presentation.screens.LoginScreen

class AuthNavGraph : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {

        navGraphBuilder.composable(NavigationRoutes.LOGIN) {

            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavigationRoutes.AUTH)
                }
            )

        }

        navGraphBuilder.composable(NavigationRoutes.AUTH) {

            AuthScreen(
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )

        }
    }
}