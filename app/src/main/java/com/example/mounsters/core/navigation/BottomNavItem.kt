package com.example.mounsters.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Explore    : BottomNavItem(NavigationRoutes.HOME,       "Explorar",   Icons.Default.LocationOn)
    object Collection : BottomNavItem(NavigationRoutes.COLLECTION, "Colección",  Icons.Default.Backpack)
    object Chat       : BottomNavItem(NavigationRoutes.CHAT,       "Chat",       Icons.Default.Chat)
    object Alerts     : BottomNavItem(NavigationRoutes.ALERTS,     "Alertas",    Icons.Default.Notifications)
    object Profile    : BottomNavItem(NavigationRoutes.PROFILE,    "Perfil",     Icons.Default.Person)
}