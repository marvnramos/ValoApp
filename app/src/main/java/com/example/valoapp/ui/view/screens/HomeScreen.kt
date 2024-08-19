package com.example.valoapp.ui.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.valoapp.data.models.CardData
import com.example.valoapp.ui.view.components.CardComponent
import com.example.valoapp.ui.viewmodel.AgentsViewModel


@Preview
@Composable
fun HomeScreen(viewModel: AgentsViewModel = viewModel()) {
    val context = LocalContext.current

    val agents by viewModel.agents.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    if (!isLoading) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            return
        }
    }
    val agentList = agents?.data ?: emptyList()

    LazyColumn(
        contentPadding = PaddingValues(8.dp)
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
                            Toast.makeText(context, agent.uuid, Toast.LENGTH_SHORT).show()
                        }
                    )
                    CardComponent(dataCard)
                }
            }
        }
    }
}