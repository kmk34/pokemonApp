package com.example.pokemon.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(
    filterText: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = filterText,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
        singleLine = true,
        placeholder = {
            Text(text = placeholder)
        },
        enabled = enabled
    )
}