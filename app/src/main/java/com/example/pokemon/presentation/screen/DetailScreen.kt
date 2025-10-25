package com.example.pokemon.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.poketmon.PokemonViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokemon.presentation.component.Header
import com.example.pokemon.presentation.component.ImageIcon
import com.example.pokemon.presentation.component.InfoRow
import com.example.pokemon.presentation.component.Loading

@Composable
fun DetailScreen(
    navController: NavController,
    itemId: String,
    koreanName: String,
    viewModel: PokemonViewModel
) {
    LaunchedEffect(itemId) { viewModel.getPokemonDetail(itemId) }

    val pokemon by viewModel.pokemon.collectAsState()

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Header(
            text = if (koreanName.isEmpty()) pokemon!!.name else koreanName,
            onBackCilck = { navController.popBackStack() },
            center = true
        )
        if (pokemon == null) {
            Loading()
            return
        }

        ImageIcon(
            imgURL = pokemon!!.sprites.front_default,
            size = 300.dp,
            description = pokemon!!.name
        )

        InfoRow(
            title = "Height:",
            contents = pokemon!!.height.toString()
        )

        InfoRow(
            title = "Weight:",
            contents = pokemon!!.weight.toString()
        )

        val slotString = pokemon!!.types.joinToString(", ") {
            it.type.name
        }
        InfoRow(
            title = "Slot:",
            contents = slotString
        )

        pokemon!!.stats.map {
            val original = it.stat.name
            val capitalized = original.replaceFirstChar { it.uppercaseChar() }
            Row {
                InfoRow(
                    title = "$capitalized: ",
                    contents = it.base_stat.toString()
                )
            }
        }

        val movesString = pokemon!!.moves.joinToString(", ") {
            it.move.name
        }
        InfoRow(
            title = "Move:",
            contents = movesString
        )

        Box(modifier = Modifier.padding(16.dp))
    }
}