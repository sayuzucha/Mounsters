package com.example.mounsters.features.Auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mounsters.core.navigation.NavigationRoutes
import com.example.mounsters.features.Auth.presentation.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Navegar al login cuando se cierra sesión
    LaunchedEffect(uiState.loggedOut) {
        if (uiState.loggedOut) {
            navController.navigate(NavigationRoutes.LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF060D1F))
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF00E5FF)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Avatar
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF00B4CC), Color(0xFF9B59B6))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.username.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre y email
                Text(
                    text = uiState.username,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = uiState.email,
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Stats row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        value = "${uiState.totalCaptured}",
                        label = "CAPTURADOS",
                        color = Color.White
                    )
                    StatDivider()
                    StatItem(
                        value = "${uiState.xp}",
                        label = "XP",
                        color = Color(0xFFF59E0B)
                    )
                    StatDivider()
                    StatItem(
                        value = "Lv${uiState.level}",
                        label = "NIVEL",
                        color = Color(0xFF6366F1)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Barra de progreso XP
                val xpForNextLevel = (uiState.level * 1000)
                val progress = (uiState.xp % 1000) / 1000f

                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progreso Nivel ${uiState.level}",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = "${uiState.xp % 1000} / 1000 XP",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF1E293B))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress.coerceIn(0f, 1f))
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(Color(0xFF6366F1), Color(0xFF00E5FF))
                                    )
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Opciones de menú
                ProfileMenuItem(
                    icon = Icons.Default.Edit,
                    iconColor = Color(0xFFFF6B6B),
                    label = "Editor perfil",
                    onClick = { /* placeholder */ }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuItem(
                    icon = Icons.Default.EmojiEvents,
                    iconColor = Color(0xFFF59E0B),
                    label = "Ranking global",
                    onClick = { /* placeholder */ }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProfileMenuItem(
                    icon = Icons.Default.Settings,
                    iconColor = Color(0xFF64748B),
                    label = "Configuración",
                    onClick = { /* placeholder */ }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Cerrar sesión — borde rojo
                ProfileMenuItem(
                    icon = Icons.Default.ExitToApp,
                    iconColor = Color(0xFFEF4444),
                    label = "Cerrar sesión",
                    labelColor = Color(0xFFEF4444),
                    borderColor = Color(0xFF7F1D1D),
                    onClick = { viewModel.logout() }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = color
        )
        Text(
            text = label,
            fontSize = 9.sp,
            letterSpacing = 1.sp,
            color = Color(0xFF64748B)
        )
    }
}

@Composable
private fun StatDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .background(Color(0xFF1E293B))
    )
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    iconColor: Color,
    label: String,
    labelColor: Color = Color.White,
    borderColor: Color = Color(0xFF1E293B),
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF0F172A)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = labelColor,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                fontSize = 20.sp,
                color = Color(0xFF475569)
            )
        }
    }
}
