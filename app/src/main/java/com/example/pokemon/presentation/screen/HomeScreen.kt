package com.example.pokemon.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokemon.presentation.component.Header
import com.example.pokemon.presentation.component.PokemonItemCard
import com.example.pokemon.presentation.component.SearchField
import com.example.poketmon.PokemonViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: PokemonViewModel) {
    viewModel.getPokemonList()
    val pokemonList by viewModel.filteredPokemonList.collectAsState()
    var filterText by remember { mutableStateOf("") }
    val isLikedFilter by viewModel.isLikedFilter.collectAsState()
    val isLastPage by viewModel.isLastPage.collectAsState()

    Column {
        Header("포켓몬 도감")

        Button(
            onClick = { viewModel.toggleLikedFilter() },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(if (isLikedFilter) "전체 보기" else "좋아요만 보기")
        }

        SearchField(
            filterText = filterText,
            onValueChange = {
                filterText = it
                viewModel.updateSearchQuery(it)  // ViewModel에 검색어 전달
            },
            placeholder =  "포켓몬 이름으로 검색",
            enabled = isLastPage)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                .fillMaxSize()
        ) {
            items(pokemonList) {item ->
                PokemonItemCard(
                    item = item,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }

//    val gridState = rememberLazyGridState()
//    val shouldLoadMore = remember {
//        derivedStateOf {
//            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
//            val totalItems = gridState.layoutInfo.totalItemsCount
//            lastVisibleItem >= totalItems - 1 // 마지막 아이템에 도달하면 true
//        }
//    }
//
//    LaunchedEffect(shouldLoadMore.value) {
//        if (shouldLoadMore.value && !isSearching) {
//            viewModel.getPokemonList()
//        }
//    }
}