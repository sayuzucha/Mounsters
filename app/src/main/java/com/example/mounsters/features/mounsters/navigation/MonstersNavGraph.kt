package com.example.mounsters.features.mounsters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mounsters.core.navigation.FeatureNavGraph
import com.example.mounsters.core.navigation.NavigationRoutes
import com.example.mounsters.features.mounsters.presentation.screens.ExploreScreen
import com.example.mounsters.features.Capture.presentation.screens.CaptureScreen

class MonstersNavGraph : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {

        navGraphBuilder.composable(NavigationRoutes.HOME) {
            ExploreScreen(
                navController = navController  // ← fix del error
            )
        }

        navGraphBuilder.composable(NavigationRoutes.MAP) { }

        navGraphBuilder.composable(NavigationRoutes.CAMERA) { }

        navGraphBuilder.composable(NavigationRoutes.MONSTERS) { }

        // Ruta para CaptureScreen cuando el jugador toca un monstruo
        navGraphBuilder.composable(
            route = "capture/{spawnId}/{monsterId}",
            arguments = listOf(
                navArgument("spawnId") { type = NavType.StringType },
                navArgument("monsterId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val spawnId = backStackEntry.arguments?.getString("spawnId") ?: ""
            val monsterId = backStackEntry.arguments?.getString("monsterId") ?: ""

            CaptureScreen(
                spawnId = spawnId,
                monsterId = monsterId,
                navController = navController
            )
        }
    }
}