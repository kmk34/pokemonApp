package com.example.poketmon

import com.example.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val respository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<PokemonDetailDto> {
        return respository.getPokemonDetail(name)
    }
}