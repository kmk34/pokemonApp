package com.example.pokemon.di

import android.content.Context
import androidx.room.Room
import com.example.pokemon.data.database.AppDatabase
import com.example.pokemon.data.database.FavoritePokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "pokemon-database").build()
    }

    @Provides
    fun provideFavoritePokemonDao(db: AppDatabase): FavoritePokemonDao = db.favoritePokemonDao()
}