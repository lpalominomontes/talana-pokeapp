package com.example.talanapokeapp.data.remote

data class PokeListResponse(
    val results: List<PokeListItemDto>
)

data class PokeListItemDto(
    val name: String,
    val url: String
) {
    fun id(): Int = url.trimEnd('/').substringAfterLast('/').toInt()
    fun imageUrl(): String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id()}.png"
}

data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val types: List<TypeSlotDto>,
    val stats: List<StatSlotDto>,
    val abilities: List<AbilitySlotDto>
)

data class TypeSlotDto(val slot: Int, val type: NamedResourceDto)
data class StatSlotDto(val base_stat: Int, val stat: NamedResourceDto)
data class AbilitySlotDto(val ability: NamedResourceDto)
data class NamedResourceDto(val name: String)