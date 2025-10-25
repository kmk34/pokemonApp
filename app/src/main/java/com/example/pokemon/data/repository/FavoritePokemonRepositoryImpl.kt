package com.example.poketmon

//import com.example.pokemon.data.database.FavoritePokemonDao
import com.example.pokemon.data.database.FavoritePokemon
import com.example.pokemon.data.database.FavoritePokemonDao
import com.example.pokemon.domain.mapper.PokemonMapper
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.domain.repository.FavoritePokemonRepository

import javax.inject.Inject
import com.example.pokemon.domain.mapper.PokemonMapper.toFavoritePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoritePokemonRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoritePokemonDao
) : FavoritePokemonRepository {
    override suspend fun addFavorite(item: Pokemon) {
        favoriteDao.insert(item.toFavoritePokemon())
    }

    override suspend fun deleteFavorite(item: Pokemon) {
        favoriteDao.delete(item.toFavoritePokemon())
    }

    override fun getFavorites(): Flow<List<Pokemon>> = flow {
        val favorites = favoriteDao.getAllFavorites()
        emit(PokemonMapper.toPokemonList(favorites))
    }.flowOn(Dispatchers.IO)

    override suspend fun isFavorite(name: String):Boolean {
        return favoriteDao.isFavorite(name)
    }
}