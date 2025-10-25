package com.example.pokemon.domain.mapper

import com.example.pokemon.data.database.FavoritePokemon
import com.example.pokemon.domain.model.Pokemon
import com.example.poketmon.PokemonDto

object PokemonMapper {

    fun PokemonDto.toPokemon(): Pokemon {
        val id = extractIdFromUrl(this.url)
        return Pokemon(
            name = this.name,
            imgURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
            koreanName = ""
        )
    }

    fun toDomainList(dtoList: List<PokemonDto>): List<Pokemon> {
        return dtoList.map {
            it.toPokemon()
        }
    }


    private fun extractIdFromUrl(url: String): Int? {
        return url.trimEnd('/')
            .split('/')
            .lastOrNull()
            ?.toIntOrNull()
    }

    fun Pokemon.toFavoritePokemon(): FavoritePokemon {
        return FavoritePokemon(
            name = this.name,
            koreanName = this.koreanName,
            imgUrl = this.imgURL ?: ""  // imgURL이 nullable이므로 기본값 지정 가능
        )
    }

    fun FavoritePokemon.toPokemon(): Pokemon{
        return Pokemon(
            name = this.name,
            koreanName = this.koreanName,
            imgURL =  this.imgUrl
        )
    }

    fun toPokemonList(favoriteLsit: List<FavoritePokemon>): List<Pokemon>{
        return favoriteLsit.map{
            it.toPokemon()
        }
    }
}


