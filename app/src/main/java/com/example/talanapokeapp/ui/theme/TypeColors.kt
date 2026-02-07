package com.example.talanapokeapp.ui.theme

import androidx.compose.ui.graphics.Color

fun pokemonTypeColor(type: String): Color =
    when (type.lowercase()) {
        "grass" -> Color(0xFF4CAF50)
        "fire" -> Color(0xFFFF7043)
        "water" -> Color(0xFF42A5F5)
        "electric" -> Color(0xFFFFD54F)
        "poison" -> Color(0xFFAB47BC)
        "bug" -> Color(0xFF9CCC65)
        "normal" -> Color(0xFFBDBDBD)
        "ground" -> Color(0xFFA1887F)
        "fairy" -> Color(0xFFF48FB1)
        "fighting" -> Color(0xFFE57373)
        "psychic" -> Color(0xFFBA68C8)
        "rock" -> Color(0xFFFFCC80)
        "ghost" -> Color(0xFF9575CD)
        "ice" -> Color(0xFF81D4FA)
        "dragon" -> Color(0xFF7986CB)
        "steel" -> Color(0xFF90A4AE)
        "dark" -> Color(0xFF8D6E63)
        else -> Color(0xFF64B5F6)
    }