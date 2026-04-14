package com.example.mounsters.features.Battle.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.mounsters.features.Battle.worker.TrophyWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject
import kotlin.math.sqrt

data class Trophy(
    val position: GeoPoint,
    val value: Int
)

sealed class BattleState {
    object Idle : BattleState()
    object SelectingTrophy : BattleState()
    object Fighting : BattleState()
    data class Victory(val newLevel: Int, val trophyValue: Int) : BattleState()
    data class Error(val msg: String) : BattleState()
}

data class BattleUiState(
    val trophies: List<Trophy> = emptyList(),
    val battleState: BattleState = BattleState.Idle
)

@HiltViewModel
class BattleViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(BattleUiState())
    val uiState: StateFlow<BattleUiState> = _uiState.asStateFlow()

    fun initBattle(userLat: Double, userLng: Double) {
        val trophies = generateTrophies(userLat, userLng)
        _uiState.value = BattleUiState(
            trophies = trophies,
            battleState = BattleState.SelectingTrophy
        )
    }

    fun startBattle(monsterId: String, monsterName: String,
                    userLat: Double, userLng: Double) {
        val trophies = _uiState.value.trophies
        val nearest  = nearestTrophy(userLat, userLng, trophies)

        _uiState.value = _uiState.value.copy(battleState = BattleState.Fighting)

        val request = TrophyWorker.buildRequest(monsterId, monsterName, nearest.value)

        workManager.enqueueUniqueWork(
            "battle_$monsterId",
            ExistingWorkPolicy.KEEP,
            request
        )

        viewModelScope.launch {
            workManager.getWorkInfoByIdFlow(request.id).collect { info ->
                when (info?.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        _uiState.value = _uiState.value.copy(
                            battleState = BattleState.Victory(0, nearest.value)
                        )
                    }
                    WorkInfo.State.FAILED -> {
                        _uiState.value = _uiState.value.copy(
                            battleState = BattleState.Error("La batalla falló")
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun generateTrophies(lat: Double, lng: Double): List<Trophy> {
        val radius = 0.008
        return List(3) {
            Trophy(
                position = GeoPoint(
                    lat + (Math.random() - 0.5) * radius,
                    lng + (Math.random() - 0.5) * radius
                ),
                value = (1..20).random()
            )
        }
    }

    private fun nearestTrophy(lat: Double, lng: Double, trophies: List<Trophy>): Trophy {
        return trophies.minByOrNull { t ->
            val dLat = t.position.latitude - lat
            val dLng = t.position.longitude - lng
            sqrt(dLat * dLat + dLng * dLng)
        } ?: trophies.first()
    }
}