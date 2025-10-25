package com.example.pokemon.presentation.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.poketmon.PokemonViewModel

@Composable
fun MainNavHost(navController: NavHostController = rememberNavController()) {
    val pokemonViewModel: PokemonViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController, viewModel = pokemonViewModel)
        }
        composable(
            route = "detail/{itemId}/{koreanName}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.StringType },
                navArgument("koreanName") { type = NavType.StringType }
            )) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            val koreanName = backStackEntry.arguments?.getString("koreanName") ?: ""
            DetailScreen(
                navController = navController,
                itemId = itemId,
                koreanName = koreanName,
                viewModel = pokemonViewModel
            )
        }
    }
}