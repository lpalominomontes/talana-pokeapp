package com.example.talanapokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.talanapokeapp.ui.home.HomeScreen
import com.example.talanapokeapp.ui.home.HomeViewModel
import com.example.talanapokeapp.ui.theme.TalanaPokeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.talanapokeapp.ui.detail.DetailViewModel
import com.example.talanapokeapp.ui.detail.DetailScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TalanaPokeAppTheme {

                var selected by remember { mutableStateOf<String?>(null) }

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (selected == null) {
                        HomeScreen(
                            viewModel = homeViewModel,
                            onSelect = { name ->
                                selected = name
                            }
                        )
                    } else {
                        val repo = dagger.hilt.android.EntryPointAccessors.fromApplication(
                            applicationContext,
                            com.example.talanapokeapp.di.RepoEntryPoint::class.java
                        ).repo()
                        val detailViewModel: DetailViewModel = viewModel(
                            key = selected!!,
                                factory = DetailViewModel.Factory(
                                    name = selected!!,
                                    repo = repo
                                )
                            )
                        val state by detailViewModel.state.collectAsState()
                        DetailScreen(
                            state = state,
                            onBack = { selected = null },
                            onToggleFavorite = { detailViewModel.toggleFavorite()}
                        )
                    }
                }
            }
        }
    }
}