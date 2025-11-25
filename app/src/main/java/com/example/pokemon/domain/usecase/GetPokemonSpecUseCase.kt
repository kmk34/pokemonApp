package com.example.poketmon

import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonSpecUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(pokemonList: List<Pokemon>): Flow<Pokemon> = flow {
        val fetchedNames = mutableSetOf<String>()
        for (pokemon in pokemonList) {
            if (!fetchedNames.contains(pokemon.name)) {
                fetchedNames.add(pokemon.name)
                val spec = repository.getPokemonSpec(pokemon.name).first()
                val koName = spec.names.find { it.language.name == "ko" }?.name
                val updated = if (koName != null) pokemon.copy(koreanName = koName) else pokemon
                emit(updated)
            } else {
                emit(pokemon)
            }
        }
    }
}