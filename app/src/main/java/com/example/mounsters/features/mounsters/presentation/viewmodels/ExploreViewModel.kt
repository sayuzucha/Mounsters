package com.example.mounsters.features.mounsters.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mounsters.core.network.ws.WebSocketManager
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.domain.usecases.GetNearbySpawnsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getNearbySpawnsUseCase: GetNearbySpawnsUseCase
) : ViewModel() {

    // ==============================
    // WEBSOCKET
    // ==============================

    private val wsManager = WebSocketManager()

    private var myUsername: String? = null

    private val _chatMessages =
        MutableStateFlow<List<Pair<String,String>>>(emptyList())

    val chatMessages: StateFlow<List<Pair<String,String>>> =
        _chatMessages.asStateFlow()

    init {
        _chatMessages.value =
            listOf("System" to "Bienvenido al chat")
    }

    fun connectSocket(token: String) {
        println("WS: intentando conectar")

        wsManager.connect(token)

        viewModelScope.launch {

            wsManager.events.collect { json: JSONObject ->

                when(json.getString("type")) {

                    // =========================
                    // AUTH OK
                    // =========================

                    "auth_ok" -> {

                        val user = json.getJSONObject("user")
                        myUsername = user.getString("username")

                        _chatMessages.value =
                            _chatMessages.value +
                                    ("System" to "Conectado como $myUsername")

                    }

                    // =========================
                    // CHAT
                    // =========================

                    "chat" -> {

                        val player = json.getString("player")
                        val message = json.getString("message")

                        val displayName =
                            if(player == myUsername) "Yo" else player

                        _chatMessages.value =
                            _chatMessages.value +
                                    (displayName to message)

                    }

                    // =========================
                    // SPAWN
                    // =========================

                    "spawn" -> {

                        val lat = json.getDouble("lat")
                        val lng = json.getDouble("lng")

                        loadNearbySpawns(lat, lng)

                    }
                }

            }

        }

    }

    fun sendChat(message: String) {

        wsManager.sendChat(message)

    }


    // ==============================
    // SPAWNS (TU CÓDIGO ORIGINAL)
    // ==============================

    private val _spawns =
        MutableStateFlow<List<Spawn>>(emptyList())

    val spawns: StateFlow<List<Spawn>> =
        _spawns.asStateFlow()

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading.asStateFlow()

    fun loadNearbySpawns(
        lat: Double,
        lng: Double,
        radius: Int = 500
    ) {

        viewModelScope.launch {

            _loading.value = true

            try {

                val result = getNearbySpawnsUseCase(
                    lat = lat,
                    lng = lng,
                    radius = radius
                )

                _spawns.value = result

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                _loading.value = false

            }

        }

    }

}