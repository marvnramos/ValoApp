package com.example.valoapp.ui.view.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.valoapp.data.models.maps.Map
import com.example.valoapp.ui.view.components.MapCardComponent
import com.example.valoapp.ui.view.components.MapModal
import com.example.valoapp.ui.viewmodel.MapsViewModel

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
    var showDialog by remember { mutableStateOf(false) }
    var selectedMap by remember {
        mutableStateOf("")
    }

    val mapsListCleaned: MutableList<Map> = mutableListOf()
    for (map in mapsList) {
        if (map.displayName != "Basic Training" && map.displayName != "The Range") {
            mapsListCleaned.add(map)
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(mapsListCleaned.chunked(2)) { mapPair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                mapPair.forEach { map ->
                    MapCardComponent(
                        map, modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .aspectRatio(1f),
                        onClick = {
                            selectedMap = map.uuid
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
    if (showDialog) {
        MapModal(mapUUID = selectedMap, onDismissRequest = { showDialog = false })
    }
}