package com.example.poketmon

import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.FavoritePokemonRepository
import javax.inject.Inject

class AddFavoritePokemonUseCase @Inject constructor(
    private val respository: FavoritePokemonRepository
) {
    suspend operator fun invoke(item: Pokemon) {
        respository.addFavorite(item)
    }
}