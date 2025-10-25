package com.example.pokemon.domain.repository

import com.example.poketmon.PokemonDetailDto
import com.example.poketmon.PokemonDto
import com.example.poketmon.PokemonListDto
import com.example.poketmon.PokemonSpecDto
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(limit:Int, offset:Int): Flow<PokemonListDto>
    fun getPokemonDetail(name: String): Flow<PokemonDetailDto>
    fun getPokemonSpec(name:String): Flow<PokemonSpecDto>
}