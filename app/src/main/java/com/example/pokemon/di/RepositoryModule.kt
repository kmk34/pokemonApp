package com.example.pokemon.di

import com.example.pokemon.domain.repository.FavoritePokemonRepository
import com.example.pokemon.domain.repository.PokemonRepository
import com.example.poketmon.FavoritePokemonRepositoryImpl
//import com.example.poketmon.FavoritePokemonRepositoryImpl
import com.example.poketmon.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    abstract fun bindFavoritePokemonRepository(
        impl: FavoritePokemonRepositoryImpl
    ): FavoritePokemonRepository
}