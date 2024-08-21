package com.example.valoapp.ui.view.screens

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.valoapp.data.models.agents.Agent
import com.example.valoapp.data.models.CardData
import com.example.valoapp.data.models.ModalData
import com.example.valoapp.ui.view.components.AgentModal
import com.example.valoapp.ui.view.components.CardComponent
import com.example.valoapp.ui.view.components.SearchBarComponent
import com.example.valoapp.ui.viewmodel.AgentsViewModel
import com.example.valoapp.utils.matchesSearchCriteria


@Preview
@Composable
fun HomeScreen(viewModel: AgentsViewModel = viewModel()) {
    val agents by viewModel.agentsResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedAgentUUID by remember { mutableStateOf("") }
    val toSearchData = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val agentList = agents?.data ?: emptyList()


    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarComponent(toSearchData)

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

            toSearchData.value.isEmpty() && agents != null -> {
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
                                        .aspectRatio(1f),
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

            toSearchData.value.isNotEmpty() && agents != null -> {
                val customAgentList: MutableList<Agent> = mutableListOf()

                for (agent in agentList) {
                    if (matchesSearchCriteria(agent, toSearchData.value)) {
                        customAgentList.add(agent)
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(customAgentList.chunked(2)) { agentPair ->
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
                                        .aspectRatio(1f),
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