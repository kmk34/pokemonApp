package com.example.pokemon.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritePokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoritePokemon)

    @Delete
    suspend fun delete(favorite: FavoritePokemon)

    @Query("SELECT * FROM favorite_pokemon")
    suspend fun getAllFavorites(): List<FavoritePokemon>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemon WHERE name = :name)")
    suspend fun isFavorite(name: String): Boolean
}
