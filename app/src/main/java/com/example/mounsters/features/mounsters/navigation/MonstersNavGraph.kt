package com.example.mounsters.features.mounsters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mounsters.core.navigation.FeatureNavGraph
import com.example.mounsters.core.navigation.NavigationRoutes
import com.example.mounsters.features.mounsters.presentation.screens.ExploreScreen

class MonstersNavGraph : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {

        // Pantalla principal después del login
        navGraphBuilder.composable(NavigationRoutes.HOME) {

            ExploreScreen(
                // si en un futuro quieres navegación desde ExploreScreen
                // puedes pasar lambdas aquí
            )

        }

        // Puedes agregar más rutas de Monsters aquí
        // Ejemplo: cámara, perfil del monstruo, colección, etc.
        navGraphBuilder.composable(NavigationRoutes.MAP) {
            // MapScreen() -> si luego quieres mostrar mapa completo
        }

        navGraphBuilder.composable(NavigationRoutes.CAMERA) {
            // CameraScreen() -> captura del monstruo
        }

        navGraphBuilder.composable(NavigationRoutes.MONSTERS) {
            // MonstersCollectionScreen() -> lista de monstruos capturados
        }
    }
}