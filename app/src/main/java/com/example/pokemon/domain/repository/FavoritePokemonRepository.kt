package com.example.pokemon.domain.repository

import com.example.pokemon.data.database.FavoritePokemon
import com.example.pokemon.domain.model.Pokemon
import com.example.poketmon.PokemonDetailDto
import com.example.poketmon.PokemonDto
import com.example.poketmon.PokemonListDto
import com.example.poketmon.PokemonSpecDto
import kotlinx.coroutines.flow.Flow

interface FavoritePokemonRepository {
    suspend fun addFavorite(item: Pokemon)
    suspend fun deleteFavorite(item: Pokemon)
    fun getFavorites(): Flow<List<Pokemon>>
    suspend fun isFavorite(name: String): Boolean
}