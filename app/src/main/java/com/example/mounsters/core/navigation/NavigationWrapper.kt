package com.example.mounsters.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mounsters.features.Auth.navigation.AuthNavGraph
import com.example.mounsters.features.mounsters.navigation.MonstersNavGraph

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Rutas donde NO se muestra el bottom bar
    val routesWithoutBottomBar = listOf(
        NavigationRoutes.SPLASH,
        NavigationRoutes.LOGIN,
        NavigationRoutes.AUTH,
        "capture/{spawnId}/{monsterId}"
    )

    val showBottomBar = currentDestination?.route !in routesWithoutBottomBar

    val bottomNavItems = listOf(
        BottomNavItem.Explore,
        BottomNavItem.Collection,
        BottomNavItem.Chat,
        BottomNavItem.Alerts,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFF0F172A),
                    contentColor = Color.White
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.route
                        } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = if (selected) Color(0xFF00E5FF) else Color.White
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    color = if (selected) Color(0xFF00E5FF) else Color.White
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color(0xFF1E293B)
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.SPLASH,
            modifier = Modifier.padding(padding)
        ) {
            AuthNavGraph().registerGraph(this, navController)
            MonstersNavGraph().registerGraph(this, navController)
        }
    }
}