package com.example.talanapokeapp.data

import com.example.talanapokeapp.data.local.FavoriteDao
import com.example.talanapokeapp.data.local.FavoritePokemonEntity
import com.example.talanapokeapp.data.remote.PokeApi
import com.example.talanapokeapp.data.remote.PokeListItemDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokeApi,
    private val favoriteDao: FavoriteDao
) {

    suspend fun getPokemonList(
        limit: Int = 20,
        offset: Int = 0
    ): List<PokeListItemDto> {
        return api.getPokemonList(limit, offset).results
    }

    suspend fun getPokemonDetail(name: String) =
        api.getPokemonDetail(name.lowercase())

    fun getFavorites(): Flow<List<FavoritePokemonEntity>> =
        favoriteDao.getFavorites()

    suspend fun isFavorite(name: String): Boolean =
        favoriteDao.isFavorite(name)

    suspend fun toggleFavorite(name: String) {
        val key = name.lowercase()
        if (favoriteDao.isFavorite(key)) {
            favoriteDao.removeFavorite(key)
        } else {
            favoriteDao.addFavorite(
                FavoritePokemonEntity(name = key)
            )
        }
    }
}