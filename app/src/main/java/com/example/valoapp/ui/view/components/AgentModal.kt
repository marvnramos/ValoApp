package com.example.valoapp.ui.view.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.valoapp.data.models.ModalData
import com.example.valoapp.ui.viewmodel.AgentViewModel

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
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
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
                        Image(
                            painter = rememberAsyncImagePainter(agent.bustPortrait),
                            contentDescription = imageDescription,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(160.dp)
                        )
                        Text(
                            text = agent.displayName,
                            modifier = Modifier.padding(16.dp),
                        )
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