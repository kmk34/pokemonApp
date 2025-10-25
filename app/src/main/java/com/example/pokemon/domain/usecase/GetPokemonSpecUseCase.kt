package com.example.poketmon

import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonSpecUseCase @Inject constructor(
    private val respository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<PokemonSpecDto> {
        return respository.getPokemonSpec(name)
    }
}