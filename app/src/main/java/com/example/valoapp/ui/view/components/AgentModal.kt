package com.example.valoapp.ui.view.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.valoapp.data.models.ModalData
import com.example.valoapp.ui.viewmodel.AgentViewModel
import com.example.valoapp.utils.hexToColorInt


@Composable
fun AgentModal(data: ModalData) {
    val (agentUUID, onDismissRequest, imageDescription) = data
    val context = LocalContext.current

    val viewModel: AgentViewModel = viewModel()
    val agentResponse by viewModel.agentResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var selectedOption by remember { mutableStateOf<String?>("Agent") }

    LaunchedEffect(agentUUID) {
        viewModel.fetchAgent(agentUUID)
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
                        Log.d("AgentModal", "Error: $errorMessage")
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        onDismissRequest()
                    }

                    agentResponse != null -> {
                        val agent = agentResponse!!.data
                        val colors = agent.backgroundGradientColors
                        val colorList = mutableListOf<Color>()

                        for (color in colors) {
                            val parsedColor = hexToColorInt(color)
                            colorList.add(Color(parsedColor))
                        }

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
                                elevation = CardDefaults.cardElevation(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.verticalGradient(colors = colorList),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(agent.bustPortrait),
                                        contentDescription = imageDescription,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {

                            Row(modifier = Modifier.fillMaxWidth()) {
                                SelectionComponent(
                                    isSelected = selectedOption == "Agent",
                                    onStatusSelected = { newSelectedState ->
                                        selectedOption = if (newSelectedState) "Agent" else null
                                    },
                                    statusText = "Agent",
                                    modifier = Modifier.weight(1f)
                                )

                                SelectionComponent(
                                    isSelected = selectedOption == "Rol",
                                    onStatusSelected = { newSelectedState ->
                                        selectedOption = if (newSelectedState) "Rol" else null
                                    },
                                    statusText = "Rol",
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            selectedOption.let { it ->
                                if (it == "Agent") {
                                    Row {
                                        Text(
                                            text = "Name:",
                                            modifier = Modifier.padding(16.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = agent.displayName,
                                            modifier = Modifier.padding(16.dp),
                                        )
                                    }
                                    Text(
                                        text = "Description:",
                                        modifier = Modifier.padding(16.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = agent.description,
                                        modifier = Modifier.padding(16.dp),
                                    )
                                } else if (it == "Rol") {
                                    Row {
                                        Text(
                                            text = "Rol:",
                                            modifier = Modifier.padding(16.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                        agent.role?.let {
                                            Text(
                                                text = it.displayName,
                                                modifier = Modifier.padding(16.dp),
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Description:",
                                        modifier = Modifier.padding(16.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = agent.role?.description
                                            ?: "No description available.",
                                        modifier = Modifier.padding(16.dp),
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