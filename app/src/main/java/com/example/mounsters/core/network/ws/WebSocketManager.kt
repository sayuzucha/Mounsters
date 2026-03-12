package com.example.mounsters.core.network.ws

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class WebSocketManager {

    private var webSocket: WebSocket? = null

    private val client = OkHttpClient.Builder()
        .pingInterval(15, TimeUnit.SECONDS)
        .build()

    private val _events = MutableSharedFlow<JSONObject>(
        replay = 0,
        extraBufferCapacity = 64
    )

    val events: SharedFlow<JSONObject> = _events

    fun connect(token: String) {

        val request = Request.Builder()
            .url("ws://192.168.1.150:3001")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {

                val auth = JSONObject()
                auth.put("type", "auth")
                auth.put("token", token)

                webSocket.send(auth.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {

                println("WS RECIBIDO: $text")

                val json = JSONObject(text)

                _events.tryEmit(json)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                t.printStackTrace()
            }
        })
    }

    fun sendChat(message: String) {

        val json = JSONObject()

        json.put("type", "chat")
        json.put("message", message)

        webSocket?.send(json.toString())
    }

    fun close() {
        webSocket?.close(1000, "Closed")
    }
}