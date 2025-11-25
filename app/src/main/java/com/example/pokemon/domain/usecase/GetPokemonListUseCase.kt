package com.example.poketmon

import com.example.pokemon.domain.mapper.PokemonMapper
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class PokemonPage(val pokemons: List<Pokemon>, val isLastPage: Boolean)

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    private var offset = 0
    private val limit = 100
    private val totalItemCnt = 300

    operator fun invoke(): Flow<PokemonPage> = flow {
        while (offset < totalItemCnt) {
            val response = repository.getPokemonList(limit, offset).first()
            val newPokemonList = PokemonMapper.toDomainList(response.results)
            val isLastPage = offset + limit >= totalItemCnt
            emit(PokemonPage(newPokemonList, isLastPage))
            offset += limit
        }
    }
}