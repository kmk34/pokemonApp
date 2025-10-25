package com.example.poketmon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.pokemon.data.database.FavoritePokemon
import com.example.pokemon.domain.mapper.PokemonMapper
import com.example.pokemon.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.pokemon.utils.MyLog
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transformWhile

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
    private val fetchedKoreanNames = mutableSetOf<String>()

    private var offset = 0
    private val limit = 100
    private var totalItemcnt = -1
    private var isLastPage = false

    private val _favorites = MutableStateFlow<List<Pokemon>>(emptyList())
    val favorites: StateFlow<List<Pokemon>> = _favorites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        //todo: 로딩
        MyLog.d("[mk] PokemonViewModel init")
        getFavoriteList()
    }

    fun getFavoriteList(){
        viewModelScope.launch {
            getFavoritesPokemonUseCase()
                .catch {}
                .collect {
                    MyLog.d("[mk][isFavorite] favorites : $it")
                    _favorites.value = it
                }
        }
    }

    fun addFavorite(item: Pokemon){
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

    fun deleteFavorite(item: Pokemon){
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
        MyLog.d("[mk] getPokemonList()")
        if (isLoading.value || isLastPage) return
        if (totalItemcnt != -1 && offset >= totalItemcnt) {
            isLastPage = true
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            getPokemonListUseCase(limit, offset)
                .catch { e -> _isLoading.value = false }
                .collect { response ->
                    totalItemcnt = response.count

                    val newPokemonList = PokemonMapper.toDomainList(response.results)

                    _pokemonList.value += newPokemonList

                    offset += limit

                    if (offset >= totalItemcnt) isLastPage = true

                    _isLoading.value = false

                    newPokemonList.map { item ->
                        if (fetchedKoreanNames.contains(item.name))
                            return@map
                        else
                            fetchedKoreanNames.add(item.name)

                        viewModelScope.launch {
                            getPokemonSpecUseCase(item.name)
                                .catch {}
                                .collect {
                                    val Names = it.names.find { it.language.name == "ko" }
                                    val currentList = _pokemonList.value.toMutableList()
                                    val index = currentList.indexOfFirst { it.name == item.name }
                                    val isFavorite = _favorites.value.any { it.name == item.name }
//                                    MyLog.d("[mk][isFavorite] isFavorite: $isFavorite")
                                    if (index != -1 && Names?.name != null) {
                                        val updatedItem =
                                            currentList[index].copy(koreanName = Names.name, isFavorite = isFavorite)
                                        currentList[index] = updatedItem
                                        _pokemonList.value = currentList.toList() // 상태 변경 트리거
                                    }
                                }
                        }
                    }


                }
        }
    }

    fun searchPokemon(name: String): Pokemon? {
        return pokemonList.value.find { it.name.contains(name) }
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

    fun isLastPage(): Boolean {
        return isLastPage
    }

//    suspend fun isFavorite(name: String): Boolean {
//
//        return isFavoritePokemonUseCase(name)
//    }
}