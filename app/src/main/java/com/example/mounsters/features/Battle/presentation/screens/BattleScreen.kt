package com.example.mounsters.features.Battle.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mounsters.features.Battle.presentation.viewmodels.BattleState
import com.example.mounsters.features.Battle.presentation.viewmodels.BattleViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BattleScreen(
    monsterId: String,
    monsterName: String,
    viewModel: BattleViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Ubicación fija igual que ExploreScreen
    val userLat = 16.776
    val userLng = -93.112

    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
        viewModel.initBattle(userLat, userLng)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Batalla — $monsterName",
                        color = Color(0xFF00E5FF),
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A)
                )
            )
        },
        containerColor = Color(0xFF060D1F)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState.battleState) {

                // ── Mapa con trofeos ──────────────────────────────
                is BattleState.SelectingTrophy -> {
                    Column(modifier = Modifier.fillMaxSize()) {

                        // Mapa ocupa 65% de la pantalla
                        AndroidView(
                            factory = { ctx ->
                                MapView(ctx).apply {
                                    setTileSource(TileSourceFactory.MAPNIK)
                                    setMultiTouchControls(true)
                                    controller.setZoom(15.0)
                                    controller.setCenter(GeoPoint(userLat, userLng))
                                }
                            },
                            update = { map ->
                                map.overlays.clear()

                                // Marcador del jugador
                                Marker(map).apply {
                                    position = GeoPoint(userLat, userLng)
                                    title = "Tú"
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                    map.overlays.add(this)
                                }

                                // 3 trofeos
                                uiState.trophies.forEachIndexed { i, trophy ->
                                    Marker(map).apply {
                                        position = trophy.position
                                        title = "Trofeo ${i + 1} — +${trophy.value} niveles"
                                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                        map.overlays.add(this)
                                    }
                                }
                                map.invalidate()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.65f)
                        )

                        // Panel inferior
                        Column(
                            modifier = Modifier
                                .weight(0.35f)
                                .fillMaxWidth()
                                .background(Color(0xFF0F172A))
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Hay 3 trofeos en el mapa",
                                color = Color(0xFF94A3B8),
                                fontSize = 13.sp
                            )
                            Text(
                                "$monsterName irá al más cercano",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Trofeos: ${uiState.trophies.map { "+${it.value}" }.joinToString(" · ")}",
                                color = Color(0xFFF59E0B),
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    viewModel.startBattle(
                                        monsterId, monsterName, userLat, userLng
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF00E5FF)
                                )
                            ) {
                                Text(
                                    "¡Iniciar batalla! 🏆",
                                    color = Color(0xFF060D1F),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // ── Peleando ──────────────────────────────────────
                is BattleState.Fighting -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF00E5FF),
                            modifier = Modifier.size(72.dp)
                        )
                        Spacer(Modifier.height(24.dp))
                        Text(
                            "$monsterName está en camino al trofeo...",
                            color = Color.White,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // ── Victoria ──────────────────────────────────────
                is BattleState.Victory -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("🏆", fontSize = 80.sp)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "¡$monsterName ganó +${state.trophyValue} niveles!",
                            color = Color(0xFFF59E0B),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(32.dp))
                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00E5FF)
                            )
                        ) {
                            Text(
                                "Regresar a colección",
                                color = Color(0xFF060D1F),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // ── Error ─────────────────────────────────────────
                is BattleState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("❌ ${state.msg}", color = Color(0xFFEF4444))
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = onBack) { Text("Regresar") }
                    }
                }

                else -> Unit
            }
        }
    }
}