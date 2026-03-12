package com.example.mounsters.features.capture.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mounsters.features.capture.presentation.screens.CaptureScreen

fun NavGraphBuilder.captureNavGraph(
    navController: NavController
) {

    composable(
        route = "capture/{spawnId}/{monsterId}/{monsterImage}",
        arguments = listOf(
            navArgument("spawnId") { type = NavType.StringType },
            navArgument("monsterId") { type = NavType.StringType },
            navArgument("monsterImage") { type = NavType.StringType }
        )
    ) { backStackEntry ->

        val spawnId =
            backStackEntry.arguments?.getString("spawnId") ?: ""

        val monsterId =
            backStackEntry.arguments?.getString("monsterId") ?: ""

        val monsterImage =
            backStackEntry.arguments?.getString("monsterImage") ?: ""

        CaptureScreen(
            spawnId = spawnId,
            monsterId = monsterId,
            monsterImage = monsterImage
        )

    }

}