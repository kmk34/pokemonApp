package com.example.pokemon.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemon")
data class FavoritePokemon(
    @PrimaryKey val name: String,
    val koreanName: String,
    val imgUrl: String
)
