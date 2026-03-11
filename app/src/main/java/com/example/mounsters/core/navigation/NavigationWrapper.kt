// core/navigation/NavigationWrapper.kt
package com.example.mounsters.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mounsters.features.Auth.navigation.AuthNavGraph

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.LOGIN,
        modifier = modifier
    ) {
        AuthNavGraph().registerGraph(this, navController)
    }
}