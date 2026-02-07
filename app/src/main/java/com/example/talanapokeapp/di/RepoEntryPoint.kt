package com.example.talanapokeapp.di

import com.example.talanapokeapp.data.PokemonRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepoEntryPoint {
    fun repo(): PokemonRepository
}