package com.example.poketmon

import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val respository: PokemonRepository
) {
    operator fun invoke(limit:Int, offset:Int): Flow<PokemonListDto> {
        return respository.getPokemonList(limit, offset)
    }
}