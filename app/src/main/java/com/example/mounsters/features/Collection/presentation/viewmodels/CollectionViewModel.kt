package com.example.mounsters.features.Collection.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mounsters.core.util.TokenManager
import com.example.mounsters.features.Collection.domain.entities.CapturedMonster
import com.example.mounsters.features.Collection.domain.usecases.GetCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CollectionUiState(
    val monsters: List<CapturedMonster> = emptyList(),
    val totalCaptured: Int = 0,
    val totalRare: Int = 0,
    val totalXp: Int = 0,
    val isLoading: Boolean = true
)

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    init {
        loadCollection()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            // mismo valor que CaptureViewModel usa al guardar
            val userId = tokenManager.getToken() ?: "local_user"

            getCollectionUseCase(userId)
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
                .catch { _uiState.value = _uiState.value.copy(isLoading = false) }
                .collect { monsters ->
                    _uiState.value = CollectionUiState(
                        monsters = monsters,
                        totalCaptured = monsters.size,
                        totalRare = monsters.count {
                            it.monsterRarity in listOf("rare", "legendary")
                        },
                        totalXp = monsters.sumOf { it.xp },
                        isLoading = false
                    )
                }
        }
    }
}
