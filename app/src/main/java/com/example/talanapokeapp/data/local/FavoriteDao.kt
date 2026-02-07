package com.example.talanapokeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoritePokemonEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE name = :name)")
    suspend fun isFavorite(name: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(pokemon: FavoritePokemonEntity)

    @Query("DELETE FROM favorites WHERE name = :name")
    suspend fun removeFavorite(name: String)
}