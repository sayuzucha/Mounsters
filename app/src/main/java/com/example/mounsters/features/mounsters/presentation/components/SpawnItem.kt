package com.example.mounsters.features.mounsters.presentation.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mounsters.features.mounsters.domain.entities.Spawn

@Composable
fun SpawnItem(
    spawn: Spawn
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = spawn.monster.name)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Type: ${spawn.monster.type}")

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Location: ${spawn.lat}, ${spawn.lng}")

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Expires: ${spawn.expiresAt}")

        }
    }
}