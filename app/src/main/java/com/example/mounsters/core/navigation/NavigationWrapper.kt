// core/navigation/NavigationWrapper.kt
package com.example.mounsters.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mounsters.features.Auth.navigation.AuthNavGraph
import com.example.mounsters.features.mounsters.navigation.MonstersNavGraph
import com.example.mounsters.features.capture.navigation.captureNavGraph

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.LOGIN,
        modifier = modifier
    ) {
        captureNavGraph(navController)
        AuthNavGraph().registerGraph(this, navController)
        MonstersNavGraph().registerGraph(this, navController)
    }
}