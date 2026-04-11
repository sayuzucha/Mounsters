package com.example.mounsters.features.mounsters.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.mounsters.presentation.viewmodels.ExploreViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val token = tokenManager.getToken() ?: ""

    val messages by viewModel.chatMessages.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var chatInput by remember { mutableStateOf("") }

    // Conectar socket si no está conectado
    LaunchedEffect(Unit) {
        viewModel.connectSocket(token)
    }

    // Auto-scroll al último mensaje
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "💬 Chat Global",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Jugadores conectados",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A)
                )
            )
        },
        containerColor = Color(0xFF0F172A)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // =========================
            // LISTA DE MENSAJES
            // =========================
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(messages) { (player, msg) ->
                    ChatBubble(player = player, message = msg)
                }
            }

            // =========================
            // INPUT
            // =========================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E293B))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = chatInput,
                    onValueChange = { chatInput = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            Color(0xFF334155),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    ),
                    decorationBox = { innerTextField ->
                        if (chatInput.isEmpty()) {
                            Text(
                                "Escribe un mensaje...",
                                color = Color(0xFF64748B),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (chatInput.isNotBlank()) {
                            viewModel.sendChat(chatInput)
                            chatInput = ""
                            scope.launch {
                                if (messages.isNotEmpty()) {
                                    listState.animateScrollToItem(messages.size - 1)
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .background(
                            Color(0xFF00E5FF),
                            shape = RoundedCornerShape(50)
                        )
                        .size(46.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = Color(0xFF0F172A)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(player: String, message: String) {
    val isSystem = player == "System"
    val isMe = player == "Yo"

    when {
        isSystem -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    color = Color(0xFF64748B),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(
                            Color(0xFF1E293B),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
        isMe -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Tú",
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = message,
                        color = Color(0xFF0F172A),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .background(
                                Color(0xFF00E5FF),
                                RoundedCornerShape(16.dp, 4.dp, 16.dp, 16.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = player,
                        color = Color(0xFF00E5FF),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Text(
                        text = message,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .background(
                                Color(0xFF1E293B),
                                RoundedCornerShape(4.dp, 16.dp, 16.dp, 16.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}