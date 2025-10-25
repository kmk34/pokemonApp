package com.example.poketmon

import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.FavoritePokemonRepository
import javax.inject.Inject

class IsFavoritePokemonUseCase @Inject constructor(
    private val respository: FavoritePokemonRepository
) {
    suspend operator fun invoke(name: String): Boolean {
        return respository.isFavorite(name)
    }
}