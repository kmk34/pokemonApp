package com.example.poketmon

import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.FavoritePokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesPokemonUseCase @Inject constructor(
    private val respository: FavoritePokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> {
        return respository.getFavorites()
    }
}