package com.example.mounsters.features.mounsters.presentation.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
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
import com.example.mounsters.R
import com.example.mounsters.core.hardware.data.AndroidFlashManager
import com.example.mounsters.core.hardware.data.AndroidVibrateManager
import com.example.mounsters.core.hardware.domain.FlashManager
import com.example.mounsters.core.hardware.domain.VibrateManager
import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.presentation.viewmodels.ExploreViewModel
import kotlinx.coroutines.delay
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlinx.coroutines.launch

// Escalar imágenes de marcadores
fun getScaledDrawable(
    context: Context,
    resId: Int,
    width: Int = 80,
    height: Int = 80
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

    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val token = tokenManager.getToken() ?: ""

    val vibrateManager: VibrateManager = remember { AndroidVibrateManager(context) }
    val flashManager: FlashManager = remember { AndroidFlashManager(context) }

    val spawns by viewModel.spawns.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val messages by viewModel.chatMessages.collectAsState()

    val fixedLocation = GeoPoint(16.776, -93.112)
    var userLocation by remember { mutableStateOf(fixedLocation) }

    val notifiedSpawns = remember { mutableStateListOf<String>() }
    val scope = rememberCoroutineScope()
    var chatInput by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {

        viewModel.connectSocket(token)

        Configuration.getInstance().userAgentValue = context.packageName

        while (true) {
            viewModel.loadNearbySpawns(
                fixedLocation.latitude,
                fixedLocation.longitude
            )
            delay(5000)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Explore Monsters") }) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // MAPA
            Box(modifier = Modifier.weight(1f)) {

                if (loading) {

                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

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

                            userLocation?.let { loc ->

                                val marker = Marker(map)

                                marker.position = loc
                                marker.title = "You are here"

                                marker.setAnchor(
                                    Marker.ANCHOR_CENTER,
                                    Marker.ANCHOR_BOTTOM
                                )

                                marker.icon = getScaledDrawable(
                                    context,
                                    R.drawable.ic_player_location,
                                    30,
                                    30
                                )

                                map.overlays.add(marker)

                                map.controller.setCenter(loc)
                            }

                            spawns.forEach { spawn: Spawn ->

                                val marker = Marker(map)

                                marker.position = GeoPoint(
                                    spawn.lat,
                                    spawn.lng
                                )

                                marker.title = spawn.monster.name

                                marker.setAnchor(
                                    Marker.ANCHOR_CENTER,
                                    Marker.ANCHOR_BOTTOM
                                )

                                marker.icon = getScaledDrawable(
                                    context,
                                    R.drawable.default_monster,
                                    30,
                                    30
                                )

                                map.overlays.add(marker)

                                val spawnKey = "${spawn.lat}:${spawn.lng}"

                                if (!notifiedSpawns.contains(spawnKey)) {

                                    notifiedSpawns.add(spawnKey)

                                    if (vibrateManager.hasVibrator())
                                        vibrateManager.vibrate(500)

                                    if (flashManager.hasFlash()) {
                                        scope.launch {
                                            flashManager.blink(300)
                                        }
                                    }
                                }
                            }

                            map.invalidate()
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // CHAT
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xAA000000))
                    .padding(8.dp)
            ) {

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {

                    items(messages) { (player, msg) ->

                        Text(
                            text = "$player: $msg",
                            color = Color.White
                        )

                    }

                }

                Row {

                    BasicTextField(
                        value = chatInput,
                        onValueChange = { chatInput = it },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White)
                            .padding(4.dp)
                    )

                    Button(
                        onClick = {

                            viewModel.sendChat(chatInput)

                            chatInput = ""

                        }
                    ) {
                        Text("Enviar")
                    }

                }
            }

        }

    }

}