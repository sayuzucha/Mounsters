package com.example.mounsters.features.mounsters.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mounsters.features.mounsters.domain.entities.Spawn
import com.example.mounsters.features.mounsters.domain.usecases.GetNearbySpawnsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getNearbySpawnsUseCase: GetNearbySpawnsUseCase
) : ViewModel() {

    private val _spawns = MutableStateFlow<List<Spawn>>(emptyList())
    val spawns: StateFlow<List<Spawn>> = _spawns.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun loadNearbySpawns(lat: Double, lng: Double, radius: Int = 500) {
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