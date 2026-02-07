package com.example.talanapokeapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.talanapokeapp.data.PokemonRepository
import com.example.talanapokeapp.data.remote.PokemonDetailDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(
        val data: PokemonDetailDto,
        val isFavorite: Boolean
    ) : DetailUiState

    data class Error(val message: String) : DetailUiState
}
class DetailViewModel(
    private val name: String,
    private val repo: PokemonRepository
) : ViewModel() {
    private val _state = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val state: StateFlow<DetailUiState> = _state
    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                val detail = repo.getPokemonDetail(name)
                val fav = repo.isFavorite(name)   // ✅ AQUÍ SE LLAMA
                _state.value = DetailUiState.Success(detail, fav)
            } catch (e: Exception) {
                _state.value = DetailUiState.Error(e.message ?: "Error cargando detalle")
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            repo.toggleFavorite(name)
            val current = _state.value
            if (current is DetailUiState.Success) {
                _state.value = current.copy(isFavorite = repo.isFavorite(name))
            }
        }
    }

    class Factory(
        private val name: String,
        private val repo: PokemonRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(name, repo) as T
        }
    }
}