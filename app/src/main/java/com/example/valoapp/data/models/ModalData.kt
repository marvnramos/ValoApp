package com.example.valoapp.data.models

data class ModalData(
    val agentUUID: String,
    val onDismissRequest: () -> Unit,
    val imageDescription: String
)
