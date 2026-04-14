package com.example.mounsters.features.mounsters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mounsters.core.navigation.FeatureNavGraph
import com.example.mounsters.core.navigation.NavigationRoutes
import com.example.mounsters.features.Alerts.presentation.screens.AlertsScreen
import com.example.mounsters.features.Auth.presentation.screens.ProfileScreen
import com.example.mounsters.features.Battle.presentation.screens.BattleScreen
import com.example.mounsters.features.Capture.presentation.screens.CaptureScreen
import com.example.mounsters.features.Collection.presentation.screens.CollectionScreen
import com.example.mounsters.features.mounsters.presentation.screens.ChatScreen
import com.example.mounsters.features.mounsters.presentation.screens.ExploreScreen

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

        navGraphBuilder.composable(
            route = NavigationRoutes.BATTLE,
            arguments = listOf(
                navArgument("monsterId")   { type = NavType.StringType },
                navArgument("monsterName") { type = NavType.StringType }
            )
        ) { backStack ->
            val monsterId   = backStack.arguments?.getString("monsterId")   ?: ""
            val monsterName = backStack.arguments?.getString("monsterName") ?: ""
            BattleScreen(
                monsterId   = monsterId,
                monsterName = monsterName,
                onBack      = { navController.popBackStack() }
            )
        }

        navGraphBuilder.composable(NavigationRoutes.COLLECTION) {
            CollectionScreen(navController = navController)
        }

        navGraphBuilder.composable(NavigationRoutes.CHAT) {
            ChatScreen()
        }

        navGraphBuilder.composable(NavigationRoutes.ALERTS) {
            AlertsScreen()
        }

        navGraphBuilder.composable(NavigationRoutes.PROFILE) {
            ProfileScreen(navController = navController)
        }
    }
}