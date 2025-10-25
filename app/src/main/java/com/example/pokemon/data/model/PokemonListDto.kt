package com.example.poketmon

data class PokemonDto(
    val name: String,
    val url: String
)

data class PokemonListDto(
    val count: Int,
    val results: List<PokemonDto>
)


