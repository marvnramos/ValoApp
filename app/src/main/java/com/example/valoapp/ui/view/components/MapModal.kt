package com.example.valoapp.ui.view.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.valoapp.ui.viewmodel.MapViewModel

@Composable
fun MapModal(mapUUID: String, onDismissRequest: () -> Unit) {
    val context = LocalContext.current

    val viewModel: MapViewModel = viewModel()
    val mapResponse by viewModel.mapResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(mapUUID) {
        viewModel.fetchMap(mapUUID)
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when {
                    isLoading -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Loading...")
                        }
                    }

                    errorMessage != null -> {
                        Log.d("MapModal", "Error: $errorMessage  id: $mapUUID")
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        onDismissRequest()
                    }

                    mapResponse != null -> {
                        val map = mapResponse!!.data
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Image(
                                        painter = if (map.premierBackgroundImage != null) {
                                            rememberAsyncImagePainter(map.premierBackgroundImage)
                                        } else {
                                            rememberAsyncImagePainter(map.stylizedBackgroundImage)
                                        },
                                        contentDescription = "map image",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = map.coordinates ?: "No coordinates found",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(8.dp),
                                        textAlign = TextAlign.Center
                                    )

                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            TextButton(
                                onClick = { onDismissRequest() },
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text("Dismiss")
                            }
                        }
                    }

                    else -> {
                        Text(text = "No data available.")
                    }
                }
            }
        }
    }
}
