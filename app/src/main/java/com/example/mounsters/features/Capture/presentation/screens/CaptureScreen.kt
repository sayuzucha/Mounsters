package com.example.mounsters.features.Capture.presentation.screens

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mounsters.R
import com.example.mounsters.features.Capture.presentation.viewmodels.CaptureViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CaptureScreen(
    spawnId: String,
    monsterId: String,
    navController: NavController,         // ← ya no recibe imageUrl
    viewModel: CaptureViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    val ballScale by animateFloatAsState(
        targetValue = if (uiState.ballThrown) 0.3f else 1f,
        animationSpec = tween(durationMillis = 400),
        label = "ball_scale"
    )

    val lat = 16.776
    val lng = -93.112

    LaunchedEffect(Unit) {
        cameraPermission.launchPermissionRequest()
        viewModel.startListening {
            viewModel.captureMonster(
                spawnId = spawnId,
                monsterId = monsterId,
                lat = lat,
                lng = lng,
                nickname = "Monster"
            )
        }
    }

    LaunchedEffect(uiState.captured, uiState.failed) {
        if (uiState.captured || uiState.failed) {
            kotlinx.coroutines.delay(2000)
            navController.popBackStack()
        }
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.stopListening() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("¡Atrapa al monstruo!") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (cameraPermission.status.isGranted) {
                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        val future = ProcessCameraProvider.getInstance(ctx)
                        future.addListener({
                            val provider = future.get()
                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }
                            try {
                                provider.unbindAll()
                                provider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview
                                )
                            } catch (e: Exception) { e.printStackTrace() }
                        }, ContextCompat.getMainExecutor(ctx))
                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A2E))
                )
            }

            if (!uiState.captured && !uiState.failed) {
                Image(
                    painter = painterResource(id = R.drawable.default_monster),
                    contentDescription = "Monster",
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.Center)
                        .offset(y = (-60).dp)
                )

                Text(
                    text = "🤚 ¡Sacude el teléfono para lanzar!",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 120.dp)
                        .background(Color(0x88000000), shape = MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.default_monster),
                    contentDescription = "Pokeball",
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = (-40).dp)
                        .scale(ballScale)
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            if (uiState.captured) {
                ResultOverlay("🎉", "¡Capturado!", Color(0xCC00C853))
            }

            if (uiState.failed) {
                ResultOverlay("💨", uiState.error ?: "¡Escapó!", Color(0xCCDD2C00))
            }
        }
    }
}

@Composable
private fun ResultOverlay(emoji: String, message: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
}