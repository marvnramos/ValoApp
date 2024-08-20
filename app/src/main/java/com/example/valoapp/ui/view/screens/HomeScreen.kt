package com.example.valoapp.ui.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.valoapp.data.models.CardData
import com.example.valoapp.data.models.ModalData
import com.example.valoapp.ui.view.components.AgentModal
import com.example.valoapp.ui.view.components.CardComponent
import com.example.valoapp.ui.view.components.SearchBarSample
import com.example.valoapp.ui.viewmodel.AgentsViewModel


@Preview
@Composable
fun HomeScreen(viewModel: AgentsViewModel = viewModel()) {
    val agents by viewModel.agentsResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedAgentUUID by remember { mutableStateOf("") }

    val context = LocalContext.current
    val agentList = agents?.data ?: emptyList()


    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarSample()

        when {
            isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Loading...")
                }
            }

            errorMessage != null -> {
                Log.d("Agent Cards", "Error: $errorMessage")
                Toast.makeText(context, "Bad internet signal!", Toast.LENGTH_SHORT).show()
            }

            agents != null -> {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(agentList.chunked(2)) { agentPair ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            agentPair.forEach { agent ->
                                val dataCard = CardData(
                                    agent = agent,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp)
                                        .height(250.dp),
                                    onClick = {
                                        selectedAgentUUID = agent.uuid
                                        showDialog = true
                                    }
                                )
                                CardComponent(dataCard)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AgentModal(
            data = ModalData(
                agentUUID = selectedAgentUUID,
                onDismissRequest = { showDialog = false },
                imageDescription = "Agent Image"
            )
        )
    }
}