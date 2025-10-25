package com.example.pokemon.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageIcon(
    imgURL: String,
    size: Dp,
    description: String = ""
) {
    val imgModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .size(size)

    if (imgURL.isNotEmpty()) {
        AsyncImage(
            modifier = imgModifier,
            model = imgURL,
            contentDescription = description
        )
    } else {
        Box(
            modifier = imgModifier,
        ) {
            // 필요 시 플레이스홀더 아이콘, 심볼 넣기 가능
            // Icon(Icons.Default.Image, contentDescription = "Placeholder")
        }
    }
}