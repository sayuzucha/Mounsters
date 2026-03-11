package com.example.mounsters.features.mounsters.presentation.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import com.example.mounsters.R
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import java.net.URL

@Composable
fun MonsterMapScreen(
    userLat: Double,
    userLng: Double,
    monsters: List<Spawn>,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val map = MapView(context)

    AndroidView(
        factory = { map },
        modifier = modifier
    ) { mapView ->
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)

        // Centrar en la ubicación del usuario
        val userLocation = GeoPoint(userLat, userLng)
        mapView.controller.setCenter(userLocation)

        // Marcador del usuario
        val userMarker = Marker(mapView).apply {
            position = userLocation
            title = "Yo"
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            icon = ContextCompat.getDrawable(context, R.drawable.ic_player_location) // tu icono personalizado
        }
        mapView.overlays.add(userMarker)
        mapView.controller.setCenter(userLocation)

        // Marcadores de monstruos
        monsters.forEach { monster ->
            val marker = Marker(mapView).apply {
                position = GeoPoint(monster.lat, monster.lng)
                title = monster.monster.name
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                // URL correcta de la imagen
                val url = "http://10.0.2.2:3000${monster.monster.imageUrl}"
                icon = loadDrawableFromUrl(context, url)
                    ?: ContextCompat.getDrawable(context, R.drawable.default_monster)
            }
            mapView.overlays.add(marker)
        }

        mapView.invalidate()
    }
}

fun loadDrawableFromUrl(context: Context, url: String): Drawable? {
    return try {
        val input = URL(url).openStream()
        val bitmap = BitmapFactory.decodeStream(input)
        BitmapDrawable(context.resources, Bitmap.createScaledBitmap(bitmap, 100, 100, false))
    } catch (e: Exception) {
        null
    }
}