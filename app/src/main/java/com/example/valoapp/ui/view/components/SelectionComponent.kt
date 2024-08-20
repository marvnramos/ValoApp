package com.example.valoapp.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectionComponent(
    isSelected: Boolean,
    onStatusSelected: (Boolean) -> Unit,
    statusText: String,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = { onStatusSelected(!isSelected) },
        label = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = statusText)
            }
        },
        border = if (isSelected) BorderStroke(2.dp, Color.DarkGray) else null,
        modifier = modifier.then(Modifier.padding(4.dp))
    )
}
