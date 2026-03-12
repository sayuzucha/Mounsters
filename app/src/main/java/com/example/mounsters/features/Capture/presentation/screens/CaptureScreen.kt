package com.example.mounsters.features.capture.presentation.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mounsters.R
import com.example.mounsters.features.capture.presentation.viewmodels.CaptureViewModel

@Composable
fun CaptureScreen(
    spawnId: String,
    monsterId: String,
    monsterImage: String,
    viewModel: CaptureViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val captureSuccess by viewModel.captureSuccess.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // guardar datos del monstruo
    LaunchedEffect(Unit) {
        viewModel.setMonster(spawnId, monsterId)
    }

    // ===============================
    // CAMARA LAUNCHER
    // ===============================

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {}

    // ===============================
    // ABRIR CAMARA AL ENTRAR
    // ===============================

    LaunchedEffect(Unit) {

        val intent: Intent? = viewModel.openCamera()

        intent?.let {
            cameraLauncher.launch(it)
        }

    }

    // ===============================
    // ACELEROMETRO
    // ===============================

    LaunchedEffect(Unit) {

        viewModel.startListeningForThrow()

    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopListening()
        }
    }

    // ===============================
    // UI
    // ===============================

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // ---------------------------
        // MONSTRUO (overlay)
        // ---------------------------

        AsyncImage(
            model = monsterImage,
            contentDescription = "Monster",
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )



        Image(
            painter = painterResource(id = R.drawable.crosshair),
            contentDescription = "Aim",
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.Center)
        )

        // ---------------------------
        // ESTADO DE CARGA
        // ---------------------------

        if (loading) {

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

        }

        // ---------------------------
        // MENSAJE DE EXITO
        // ---------------------------

        if (captureSuccess) {

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "🎉 Monster Captured!",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = {
                    viewModel.resetCaptureState()
                }) {
                    Text("Continue")
                }

            }

        }

        // ---------------------------
        // ERROR
        // ---------------------------

        error?.let {

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    viewModel.resetCaptureState()
                }) {
                    Text("Retry")
                }

            }

        }

    }

}