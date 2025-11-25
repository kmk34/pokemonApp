package com.example.poketmon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.mapper.PokemonMapper
import com.example.pokemon.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.pokemon.utils.MyLog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val getPokemonSpecUseCase: GetPokemonSpecUseCase,
    private val addFavoritePokemonUseCase: AddFavoritePokemonUseCase,
    private val deleteFavoritePokemonUseCase: DeleteFavoritePokemonUseCase,
    private val getFavoritesPokemonUseCase: GetFavoritesPokemonUseCase,
    private val isFavoritePokemonUseCase: IsFavoritePokemonUseCase
) : ViewModel() {
    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList
    private val _pokemon = MutableStateFlow<PokemonDetailDto?>(null)
    val pokemon: StateFlow<PokemonDetailDto?> = _pokemon

    private val _favorites = MutableStateFlow<List<Pokemon>>(emptyList())
    val favorites: StateFlow<List<Pokemon>> = _favorites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage.asStateFlow()

    private val _isLikedFilter = MutableStateFlow(false)
    val isLikedFilter: StateFlow<Boolean> = _isLikedFilter.asStateFlow()

    fun toggleLikedFilter() {
        _isLikedFilter.value = !_isLikedFilter.value
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    init { //todo: 로딩
        MyLog.d("[mk] PokemonViewModel init")
        getFavoriteList()
    }

    val filteredPokemonList: StateFlow<List<Pokemon>> = combine(
        _pokemonList,
        _favorites,
        _isLikedFilter,
        _searchQuery
    ) { allList, favorites, isFilterOn, query ->
        var filtered = if (isFilterOn) {
            allList.filter { pokemon -> favorites.any { it.name == pokemon.name } }
        } else allList

        if (query.isNotBlank()) {
            filtered = filtered.filter {
                it.name.contains(query, ignoreCase = true) || it.koreanName.contains(
                    query,
                    ignoreCase = true
                )
            }
        }
        filtered
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getFavoriteList() {
        viewModelScope.launch {
            getFavoritesPokemonUseCase()
                .catch {}
                .collect {
                    MyLog.d("[mk][isFavorite] favorites : $it")
                    _favorites.value = it
                }
        }
    }

    fun addFavorite(item: Pokemon) {
        viewModelScope.launch {
            addFavoritePokemonUseCase(item)
        }

        val currentList = _pokemonList.value.toMutableList()
        val index = currentList.indexOfFirst { it.name == item.name }
        if (index != -1) {
            val updatedItem =
                currentList[index].copy(isFavorite = true)
            currentList[index] = updatedItem
            _pokemonList.value = currentList.toList() // 상태 변경 트리거
        }
    }

    fun deleteFavorite(item: Pokemon) {
        viewModelScope.launch {
            deleteFavoritePokemonUseCase(item)

            val currentList = _pokemonList.value.toMutableList()
            val index = currentList.indexOfFirst { it.name == item.name }
            if (index != -1) {
                val updatedItem =
                    currentList[index].copy(isFavorite = false)
                currentList[index] = updatedItem
                _pokemonList.value = currentList.toList() // 상태 변경 트리거
            }
        }
    }

    fun getPokemonList() {
        _isLoading.value = true
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getPokemonListUseCase()
                    .catch { e -> _isLoading.value = false }
                    .collect { page ->
                        _pokemonList.value += page.pokemons
                        _isLastPage.value = page.isLastPage
                        _isLoading.value = false

                        updateKoreanNames(page.pokemons)
                        updateFavorite(page.pokemons)
                    }
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }

    private fun updateKoreanNames(pokemonList: List<Pokemon>) {
        viewModelScope.launch {
            getPokemonSpecUseCase(pokemonList)
                .catch {}
                .collect { response ->
                    val currentList = _pokemonList.value.toMutableList()
                    val index = currentList.indexOfFirst { it.name == response.name }
                    val isFavorite = _favorites.value.any { it.name == response.name }
                    if (index != -1) {
                        currentList[index] = response.copy(isFavorite = isFavorite)
                        _pokemonList.value = currentList.toList()
                    }
                }
        }
    }

    private fun updateFavorite(pokemonList: List<Pokemon>) {
        viewModelScope.launch {
            for (pokemon in pokemonList) {
                val currentList = _pokemonList.value.toMutableList()
                val index = currentList.indexOfFirst { it.name == pokemon.name }
                val isFavorite = _favorites.value.any { it.name == pokemon.name }
                if (index != -1) {
                    currentList[index] = pokemon.copy(isFavorite = isFavorite)
                    _pokemonList.value = currentList.toList()
                }
            }
        }
    }

    fun getPokemonDetail(id: String?) {
        _pokemon.value = null
        if (id != null) {
            viewModelScope.launch {
                _isLoading.value = true
                getPokemonDetailUseCase(id)
                    .catch {}
                    .collect { item ->
                        MyLog.d("[mk] item Detail: $item")
                        _pokemon.value = item
                        _isLoading.value = false
                    }
            }
        }
    }
}