package com.example.mounsters.features.mounsters.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.presentation.components.SpawnItem
import com.example.mounsters.features.mounsters.presentation.viewmodels.ExploreViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val spawns by viewModel.spawns.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val context = LocalContext.current

    // Ubicación fija UPChiapas, Suchiapa
    val fixedLocation = GeoPoint(16.776, -93.112)
    var userLocation by remember { mutableStateOf(fixedLocation) }

    // Inicializar osmdroid y cargar spawns
    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
        viewModel.loadNearbySpawns(fixedLocation.latitude, fixedLocation.longitude)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Explore Monsters") }) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {

                // Mapa con AndroidView y actualización de marcadores
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            setTileSource(TileSourceFactory.MAPNIK)
                            setMultiTouchControls(true)
                            controller.setZoom(17.0)
                        }
                    },
                    update = { map ->
                        map.overlays.clear()

                        // Marcador del jugador
                        userLocation?.let { loc ->
                            val playerMarker = Marker(map)
                            playerMarker.position = loc
                            playerMarker.title = "You are here"
                            playerMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            map.overlays.add(playerMarker)
                            map.controller.setCenter(loc)
                        }

                        // Marcadores de spawns
                        spawns.forEach { spawn ->
                            val marker = Marker(map)
                            marker.position = GeoPoint(spawn.lat, spawn.lng)
                            marker.title = spawn.monster.name
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            map.overlays.add(marker)
                        }

                        map.invalidate()
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Panel inferior con lista de spawns
                if (spawns.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .align(Alignment.BottomCenter)
                            .background(Color(0xAA000000)),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(spawns) { spawn: Spawn ->
                            SpawnItem(spawn)
                        }
                    }
                } else {
                    Text(
                        text = "No monsters nearby",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(Color(0xAA000000))
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}