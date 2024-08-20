package com.example.valoapp.ui.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSample(text: MutableState<String>) {
    Column(
        Modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        Spacer(modifier = Modifier.height(8.dp))
        SearchBar(
            query = text.value,
            onQueryChange = { text.value = it },
            onSearch = {  },
            active = false,
            onActiveChange = { },
            placeholder = { Text("Search by name and role") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .semantics { traversalIndex = 0f },
        ) { }
    }
}
