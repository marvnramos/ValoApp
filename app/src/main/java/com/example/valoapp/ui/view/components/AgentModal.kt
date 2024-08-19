package com.example.valoapp.ui.view.components

import androidx.compose.ui.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.valoapp.data.models.ModalData
import com.example.valoapp.ui.viewmodel.AgentViewModel
import com.example.valoapp.utils.hexToColorInt

@Composable
@Preview
fun modalpreview(){
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AgentModal(data = ModalData(
            agentUUID = "dade69b4-4f5a-8528-247b-219e5a1facd6",
            onDismissRequest = { showDialog = false},
            imageDescription = "agent image"
        ))
    }
}

@Composable
fun AgentModal(data: ModalData) {
    val (agentUUID, onDismissRequest, imageDescription) = data
    val context = LocalContext.current

    val viewModel: AgentViewModel = viewModel()
    val agentResponse by viewModel.agentResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(agentUUID) {
        viewModel.fetchAgent(agentUUID)
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when {
                    isLoading -> {
                        Text(text = "Loading...")
                    }

                    errorMessage != null -> {
                        Log.d("AgentModal", "Error: $errorMessage")
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        onDismissRequest()
                    }

                    agentResponse != null -> {
                        val agent = agentResponse!!.data
                        val colors = agent?.backgroundGradientColors
                        val colorList = mutableListOf<Color>()

                        if (colors != null) {
                            for (color in colors) {
                                val parsedColor = hexToColorInt(color)
                                colorList.add(Color(parsedColor))
                            }
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
//                                horizontalAlignment = Arrangement.
                            ) {
                                Text(
                                    text = agent.displayName,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = "Description:",
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = agent.description,
                                    modifier = Modifier.padding(16.dp),
                                )
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