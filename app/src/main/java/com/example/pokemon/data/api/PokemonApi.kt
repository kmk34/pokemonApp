package com.example.pokemon.data.api

import com.example.poketmon.PokemonDetailDto
import com.example.poketmon.PokemonListDto
import com.example.poketmon.PokemonSpecDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonListDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetailDto

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpec(@Path("name") name: String): PokemonSpecDto
}