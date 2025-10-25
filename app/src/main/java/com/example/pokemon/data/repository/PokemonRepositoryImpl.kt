package com.example.poketmon

import com.example.pokemon.data.api.PokemonApi
import com.example.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {
    override fun getPokemonList(limit: Int, offset: Int): Flow<PokemonListDto> = flow {
        val response = api.getPokemonList(limit, offset)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getPokemonDetail(name: String): Flow<PokemonDetailDto> = flow {
        val response = api.getPokemonDetail(name)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override fun getPokemonSpec(name: String): Flow<PokemonSpecDto> = flow {
        val response = api.getPokemonSpec(name)
        emit(response)
    }.flowOn(Dispatchers.IO)
}