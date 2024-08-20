package com.example.valoapp.ui.view.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.valoapp.ui.view.components.MapCardComponent
import com.example.valoapp.ui.viewmodel.MapsViewModel
import com.example.valoapp.data.models.maps.Map

@Composable
fun MapsScreen(viewModel: MapsViewModel = viewModel()) {
    val maps by viewModel.mapsResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    val mapsList = maps?.data ?: emptyList()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                LoadingIndicator()
            }

            errorMessage != null -> {
                ErrorMessage(context, errorMessage!!)
            }

            maps != null -> {
                MapsList(mapsList)
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading...😶‍🌫️")
    }
}

@Composable
fun ErrorMessage(context: Context, errorMessage: String) {
    Log.d("Map cards", "Error: $errorMessage")
    Toast.makeText(context, "Bad internet signal!", Toast.LENGTH_SHORT).show()
    Text(
        text = "Error loading maps: $errorMessage",
        modifier = Modifier.padding(16.dp),
        color = androidx.compose.ui.graphics.Color.Red
    )
}

@Composable
fun MapsList(mapsList: List<Map>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(mapsList.chunked(2)) { mapPair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                mapPair.forEach { map ->
                    MapCardComponent(map, modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .aspectRatio(1f)
                    )
                }
            }
        }
    }
}