package com.example.poketmon

import com.google.gson.annotations.SerializedName

data class Sprites(
//    @SerializedName("front_default")
    val front_default: String,
)

data class TypeSlot(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
)

data class Stat(
    val base_stat: Int,
    val stat: StatName
)

data class StatName(
    val name: String,
)

data class MoveSlot(
    val move: Move,
)

data class Move(
    val name: String,
)

data class PokemonDetailDto(
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val stats: List<Stat>,
    val sprites: Sprites,
    val moves: List<MoveSlot>
)