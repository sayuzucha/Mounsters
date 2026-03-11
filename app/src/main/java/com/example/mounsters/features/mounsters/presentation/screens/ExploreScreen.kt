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
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.presentation.components.SpawnItem
import com.example.mounsters.features.mounsters.presentation.viewmodels.ExploreViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.example.mounsters.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable


fun getScaledDrawable(
    context: Context,
    resId: Int,
    width: Int = 80,    // ancho deseado en px
    height: Int = 80    // alto deseado en px
): Drawable? {
    val drawable = ContextCompat.getDrawable(context, resId) ?: return null
    val bitmap = (drawable as BitmapDrawable).bitmap
    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
    return BitmapDrawable(context.resources, scaledBitmap)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val spawns by viewModel.spawns.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val context = LocalContext.current

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
                            playerMarker.icon = getScaledDrawable(context, R.drawable.ic_player_location, 30, 30)
                            map.overlays.add(playerMarker)
                            map.controller.setCenter(loc)
                        }

                        // Marcadores de spawns con imagenes locales
                        spawns.forEach { spawn ->
                            val marker = Marker(map)
                            marker.position = GeoPoint(spawn.lat, spawn.lng)
                            marker.title = spawn.monster.name
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                            // Asignar imagen según el nombre del monstruo
                            marker.icon = when (spawn.monster.name) {
                                "FlameWolf" -> getScaledDrawable(context, R.drawable.flamewolf, 30, 30)
                                "AquaSerpent" -> getScaledDrawable(context, R.drawable.aquaserpent, 30, 30)
                                "VineGolem" -> getScaledDrawable(context, R.drawable.vinegolem, 30, 30)
                                "ThunderBat" -> getScaledDrawable(context, R.drawable.thunderbat, 30, 30)
                                "StoneBear" -> getScaledDrawable(context, R.drawable.stonebear, 30, 30)
                                "IceFox" -> getScaledDrawable(context, R.drawable.icefox, 30, 30)
                                "ShadowCat" -> getScaledDrawable(context, R.drawable.shadowcat, 30, 30)
                                "DragonKing" -> getScaledDrawable(context, R.drawable.dragonking, 30, 30)
                                else -> getScaledDrawable(context, R.drawable.default_monster, 30, 30)
                            }

                            map.overlays.add(marker)
                        }

                        map.invalidate()
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Lista inferior de spawns
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