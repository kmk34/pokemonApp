package com.example.pokemon.domain.model

data class Pokemon(
    val name: String,
    val imgURL: String?,
    var koreanName: String,
    var isFavorite: Boolean = false
)