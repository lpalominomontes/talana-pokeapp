package com.example.talanapokeapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.talanapokeapp.data.remote.PokeListItemDto
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSelect: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val favorites by viewModel.favoriteNames.collectAsState()

    when (val s = state) {
        HomeUiState.Loading -> LoadingView()
        is HomeUiState.Error -> ErrorView(s.message)
        is HomeUiState.Success -> PokemonList(
            list = s.list,
            favorites = favorites,
            onSelect = onSelect
        )
    }
}

@Composable
private fun LoadingView() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Composable
private fun PokemonList(
    list: List<PokeListItemDto>,
    favorites: Set<String>,
    onSelect: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = list,
            key = { it.name }
        ) { p ->
            val isFav = favorites.contains(p.name.lowercase())
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(p.name) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = p.imageUrl(),
                        contentDescription = p.name,
                        modifier = Modifier
                            .size(84.dp)
                    )
                    Spacer(Modifier
                        .height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isFav) "★" else "☆",
                            color = if (isFav) Color(0xFFFFC107) else Color.Gray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier
                            .width(6.dp))
                        Text(
                            text = p.name.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}