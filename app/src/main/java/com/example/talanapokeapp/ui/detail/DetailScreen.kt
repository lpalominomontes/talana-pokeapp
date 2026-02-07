package com.example.talanapokeapp.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.talanapokeapp.ui.theme.pokemonTypeColor

@Composable
fun DetailScreen(
    state: DetailUiState,
    onBack: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    when (state) {
        DetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        is DetailUiState.Success -> {
            val pokemon = state.data
            val fav = state.isFavorite
            val mainType = pokemon.types.firstOrNull()?.type?.name ?: "normal"
            val headerColor = pokemonTypeColor(mainType)

            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(headerColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = onBack) {
                            Text("Volver")
                        }
                        Button(onClick = onToggleFavorite) {
                            Text("Favorito")
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = if (fav) "★" else "☆",
                                color = if (fav) Color(0xFFFFC107) else Color.White
                            )
                        }
                    }
                    Text(
                        text = "#${pokemon.id}  ${pokemon.name.replaceFirstChar { it.uppercase() }}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 16.dp, top = 72.dp)
                    )
                }
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
                    contentDescription = pokemon.name,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 120.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Spacer(Modifier.height(80.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        pokemon.types.forEach { t ->
                            Surface(
                                color = pokemonTypeColor(t.type.name).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(999.dp)
                            ) {
                                Text(
                                    text = t.type.name.replaceFirstChar { it.uppercase() },
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    color = pokemonTypeColor(t.type.name)
                                )
                            }
                        }
                    }
                    Spacer(Modifier
                        .height(16.dp))
                    Text("Habilidades", style = MaterialTheme.typography.titleMedium)
                    Text(pokemon.abilities.joinToString { it.ability.name })
                    Spacer(Modifier
                        .height(16.dp))
                    Text("Stats",
                        style = MaterialTheme
                            .typography
                            .titleMedium)
                    Spacer(Modifier
                        .height(8.dp))
                    pokemon.stats.forEach { stat ->
                        val value = stat.base_stat.coerceIn(0, 200)
                        Text("${stat.stat.name}: ${stat.base_stat}")
                        LinearProgressIndicator(
                            progress = value / 200f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            color = headerColor
                        )
                        Spacer(Modifier
                            .height(8.dp))
                    }
                }
            }
        }
    }
}