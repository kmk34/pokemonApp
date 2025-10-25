package com.example.pokemon.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.R

@Composable
fun Header(
    text: String,
    onBackCilck: (() -> Unit)? = null,
    center: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .align(if (center) Alignment.Center else Alignment.CenterStart),
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        if (onBackCilck != null) {
            IconButton(
                onClick = onBackCilck
            ) {
                Icon(
                    painter = painterResource(id = R.mipmap.tb_icon_prew),
                    contentDescription = "back"
                )
            }
        }
    }
}