package com.example.valoapp.data.models

import androidx.compose.ui.Modifier
import com.example.valoapp.data.models.agents.Agent

data class CardData(
    val agent: Agent?,
    val modifier: Modifier,
    val onClick: () -> Unit
)
