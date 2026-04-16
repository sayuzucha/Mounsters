package com.example.mounsters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mounsters.core.navigation.NavigationWrapper
import com.example.mounsters.core.network.ApiService
import com.example.mounsters.core.ui.theme.MounstersTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            android.util.Log.d("FCM_TOKEN", "Token: $token")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    apiService.updateFcmToken(mapOf("token" to token))
                    android.util.Log.d("FCM_TOKEN", "Token guardado en backend ✅")
                } catch (e: Exception) {
                    android.util.Log.e("FCM_TOKEN", "Error guardando token: ${e.message}")
                }
            }
        }

        setContent {
            MounstersTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    NavigationWrapper(
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}