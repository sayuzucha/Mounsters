package com.example.mounsters.features.Auth.presentation.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mounsters.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    // Animación de la barra de progreso
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2500,
                easing = FastOutSlowInEasing
            )
        )
        delay(300)
        onSplashFinished()
    }

    // Animación de pulso del monstruo
    val pulse = remember { Animatable(1f) }
    LaunchedEffect(Unit) {
        while (true) {
            pulse.animateTo(1.08f, tween(900, easing = FastOutSlowInEasing))
            pulse.animateTo(1f,    tween(900, easing = FastOutSlowInEasing))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF060D1F)),  // fondo azul muy oscuro
        contentAlignment = Alignment.Center
    ) {

        // =========================
        // CÍRCULOS DECORATIVOS
        // =========================
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircleDecor(this)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            // =========================
            // GLOW + MONSTRUO
            // =========================
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(180.dp)
            ) {
                // Glow detrás del monstruo
                Canvas(modifier = Modifier.size(180.dp)) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x5500E5FF),
                                Color(0x2200B4CC),
                                Color.Transparent
                            ),
                            center = Offset(size.width / 2, size.height / 2),
                            radius = size.minDimension / 1.5f
                        )
                    )
                }

                // Imagen del monstruo con pulso
                Image(
                    painter = painterResource(id = R.drawable.default_monster),
                    contentDescription = "Monster",
                    modifier = Modifier
                        .size(110.dp)
                        .graphicsLayer(
                            scaleX = pulse.value,
                            scaleY = pulse.value
                        )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // =========================
            // TÍTULO
            // =========================
            Text(
                text = "MOUNSTERS",
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 6.sp,
                color = Color(0xFF00E5FF),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = "HUNT  ·  CAPTURE  ·  COLLECT",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 3.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // =========================
            // BARRA DE PROGRESO
            // =========================
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(4.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Fondo de la barra
                    drawRoundRect(
                        color = Color(0xFF1E293B),
                        size = size,
                        cornerRadius = CornerRadius(8f)
                    )
                    // Barra de progreso con gradiente
                    drawRoundRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF6366F1),
                                Color(0xFF00E5FF)
                            )
                        ),
                        size = Size(size.width * progress.value, size.height),
                        cornerRadius = CornerRadius(8f)
                    )
                }
            }
        }
    }
}

// Círculos decorativos de fondo
fun drawCircleDecor(scope: DrawScope) {
    with(scope) {
        // Círculo grande derecha
        drawCircle(
            color = Color(0x0800E5FF),
            radius = size.width * 0.6f,
            center = Offset(size.width * 0.85f, size.height * 0.75f)
        )
        // Círculo mediano izquierda
        drawCircle(
            color = Color(0x0800E5FF),
            radius = size.width * 0.4f,
            center = Offset(size.width * 0.1f, size.height * 0.3f)
        )
        // Círculo pequeño
        drawCircle(
            color = Color(0x0F6366F1),
            radius = size.width * 0.25f,
            center = Offset(size.width * 0.8f, size.height * 0.15f)
        )
    }
}