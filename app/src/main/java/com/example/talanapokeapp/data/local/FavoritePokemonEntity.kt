package com.example.talanapokeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoritePokemonEntity(
    @PrimaryKey val name: String
)