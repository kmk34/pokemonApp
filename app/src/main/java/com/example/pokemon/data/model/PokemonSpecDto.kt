package com.example.poketmon


data class Language(
    val name: String,
)

data class Names(
    val name: String,
    val language: Language
)

data class PokemonSpecDto(
    val names: List<Names>,
)