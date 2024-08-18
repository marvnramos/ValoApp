package com.example.valoapp.data.models

import androidx.compose.ui.Modifier

data class CardData(
    val agent: Agent?,
    val modifier: Modifier,
    val onClick: () -> Unit
)
