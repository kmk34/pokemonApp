package com.example.pokemon.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pokemon.R
import com.example.pokemon.domain.model.Pokemon
import com.example.poketmon.PokemonViewModel

@Composable
fun PokemonItemCard(
    item: Pokemon,
    viewModel: PokemonViewModel,
    navController: NavController,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(8.dp) // 모서리 둥글게
            )
            .clickable {
                if (!item.name.isEmpty())
                    navController.navigate("detail/${item.name}/${item.koreanName}")
            }
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(96.dp)
        ) {
            ImageIcon(
                imgURL = item.imgURL ?: "",
                size = 96.dp,
                description = item.name
            )

            IconButton(
                onClick = {
                    if (item.isFavorite)
                        viewModel.deleteFavorite(item)
                    else
                        viewModel.addFavorite(item)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = (if (item.isFavorite) painterResource(id = R.mipmap.icon_heart_sel)
                    else painterResource(id = R.mipmap.icon_heart)),
                    contentDescription = (if (item.isFavorite) "좋아요"
                    else "좋아요 취소"),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            modifier = Modifier.padding(bottom = 2.dp),
            text = item.koreanName,
            fontSize = 13.sp
        )
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = item.name,
            fontSize = 10.sp
        )
    }
}