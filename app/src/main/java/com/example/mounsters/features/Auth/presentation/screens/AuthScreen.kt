package com.example.mounsters.features.Auth.presentation.screens


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mounsters.features.Auth.domain.entities.RegisterRequest
import com.example.mounsters.features.Auth.presentation.viewmodels.AuthViewModel
import com.example.mounsters.features.Auth.presentation.viewmodels.AuthViewModelFactory


import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    factory: AuthViewModelFactory,
    onRegisterSuccess: () -> Unit
) {

    val viewModel: AuthViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showSuccessMessage by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.user) {
        if (uiState.user != null) {

            val prefs = context.getSharedPreferences("MonsterPrefs", Context.MODE_PRIVATE)

            prefs.edit {
                putString("username", uiState.user?.username ?: username)
            }

            showSuccessMessage = true
            delay(2000)

            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E1B4B),
                        Color(0xFF312E81)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "CREATE ACCOUNT",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E293B)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            showSuccessMessage = false
                            viewModel.register(
                                RegisterRequest(
                                    username = username,
                                    email = email,
                                    password = password
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !uiState.isLoading
                    ) {
                        Text("Register", fontSize = 18.sp)
                    }

                    if (uiState.isLoading) {
                        Spacer(modifier = Modifier.height(10.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    uiState.error?.let {

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = it,
                            color = Color.Red
                        )
                    }

                    if (showSuccessMessage) {

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "✓ Account created! Redirecting...",
                            color = Color.Green
                        )
                    }
                }
            }
        }
    }
}