package com.example.talanapokeapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talanapokeapp.data.PokemonRepository
import com.example.talanapokeapp.data.remote.PokeListItemDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val list: List<PokeListItemDto>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    val favoriteNames: StateFlow<Set<String>> = repository.getFavorites()
        .map { list -> list.map { it.name.lowercase() }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    init { loadPokemons()
    viewModelScope.launch { repository.getFavorites().collect{ println("FAVORITOS DB -> $it") } }}

    private fun loadPokemons() {
        viewModelScope.launch {
            try {
                val pokemons = repository.getPokemonList()
                _uiState.value = HomeUiState.Success(pokemons)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Error cargando Pokémon")
            }
        }
    }
}