package com.example.mounsters.features.mounsters.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mounsters.core.navigation.FeatureNavGraph
import com.example.mounsters.core.navigation.NavigationRoutes
import com.example.mounsters.features.Auth.presentation.screens.ProfileScreen
import com.example.mounsters.features.Capture.presentation.screens.CaptureScreen
import com.example.mounsters.features.mounsters.presentation.screens.ExploreScreen
import com.example.mounsters.features.mounsters.presentation.screens.ChatScreen
import com.example.mounsters.features.Collection.presentation.screens.CollectionScreen

class MonstersNavGraph : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {

        navGraphBuilder.composable(NavigationRoutes.HOME) {
            ExploreScreen(navController = navController)
        }

        navGraphBuilder.composable(NavigationRoutes.MAP) { }

        navGraphBuilder.composable(NavigationRoutes.CAMERA) { }

        navGraphBuilder.composable(NavigationRoutes.MONSTERS) { }

        navGraphBuilder.composable(
            route = "capture/{spawnId}/{monsterId}",
            arguments = listOf(
                navArgument("spawnId")   { type = NavType.StringType },
                navArgument("monsterId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val spawnId   = backStackEntry.arguments?.getString("spawnId")   ?: ""
            val monsterId = backStackEntry.arguments?.getString("monsterId") ?: ""

            CaptureScreen(
                spawnId       = spawnId,
                monsterId     = monsterId,
                navController = navController
            )
        }

        navGraphBuilder.composable(NavigationRoutes.COLLECTION) {
            CollectionScreen()
        }

        navGraphBuilder.composable(NavigationRoutes.CHAT) {
            ChatScreen()
        }

        navGraphBuilder.composable(NavigationRoutes.ALERTS) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Alertas próximamente")
            }
        }

        navGraphBuilder.composable(NavigationRoutes.PROFILE) {
            ProfileScreen(navController = navController)
        }
    }
}