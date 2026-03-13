package com.example.mounsters.features.Collection.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.mounsters.R
import com.example.mounsters.features.Collection.domain.entities.CapturedMonster
import com.example.mounsters.features.Collection.presentation.viewmodels.CollectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val rarityColor = mapOf(
        "common"    to Color(0xFF64748B),
        "uncommon"  to Color(0xFF22C55E),
        "rare"      to Color(0xFF6366F1),
        "legendary" to Color(0xFFF59E0B)
    )

    val typeColor = mapOf(
        "fire"     to Color(0xFFEF4444),
        "water"    to Color(0xFF3B82F6),
        "plant"    to Color(0xFF22C55E),
        "electric" to Color(0xFFFACC15),
        "earth"    to Color(0xFF92400E),
        "ice"      to Color(0xFF67E8F9),
        "dark"     to Color(0xFF7C3AED),
        "dragon"   to Color(0xFFEC4899)
    )

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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // HEADER
                item(span = { GridItemSpan(3) }) {
                    CollectionHeader(uiState.totalCaptured, uiState.totalRare, uiState.totalXp)
                }

                // GRID DE MONSTRUOS
                items(uiState.monsters) { monster ->
                    MonsterCell(
                        monster = monster,
                        borderColor = rarityColor[monster.monsterRarity] ?: Color(0xFF1E293B)
                    )
                }

                // Celda vacía (el "?" del diseño)
                if (uiState.monsters.size % 3 != 0) {
                    items(3 - (uiState.monsters.size % 3)) {
                        EmptyCell()
                    }
                }
            }
        }
    }
}

@Composable
private fun CollectionHeader(total: Int, rare: Int, xp: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Título + contador
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MI COLECCIÓN",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 3.sp,
                color = Color(0xFF00E5FF)
            )
            Text(
                text = "$total / ?",
                fontSize = 13.sp,
                color = Color(0xFF64748B)
            )
        }

        // Stats cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(value = "$total", label = "CAPTURADOS", color = Color.White, modifier = Modifier.weight(1f))
            StatCard(value = "$rare",  label = "RAROS",      color = Color(0xFF6366F1), modifier = Modifier.weight(1f))
            StatCard(value = "$xp",   label = "XP TOTAL",   color = Color(0xFFF59E0B), modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, color: Color, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F172A))
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
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
}

@Composable
private fun MonsterCell(monster: CapturedMonster, borderColor: Color) {
    Box(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F172A))
            .border(1.dp, borderColor.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        // Nivel arriba a la derecha
        Text(
            text = "Lv${monster.level}",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF59E0B),
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen del monstruo
            AsyncImage(
                model = "http://192.168.1.150:3000${monster.imageUrl}",
                contentDescription = monster.monsterName,
                error = painterResource(R.drawable.default_monster),
                placeholder = painterResource(R.drawable.default_monster),
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Nombre
            Text(
                text = monster.monsterName.uppercase(),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun EmptyCell() {
    Box(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0F172A))
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "?",
            fontSize = 28.sp,
            color = Color(0xFF1E293B),
            fontWeight = FontWeight.ExtraBold
        )
    }
}