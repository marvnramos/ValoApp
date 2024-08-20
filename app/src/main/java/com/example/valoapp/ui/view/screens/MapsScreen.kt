package com.example.valoapp.ui.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.valoapp.ui.viewmodel.MapsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun MapsScreen(viewModel: MapsViewModel = viewModel()) {
    val maps by viewModel.mapsResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when {
            isLoading -> {
                Text(text = "Loading... ‍🌫️")
            }

            !isLoading && errorMessage != null -> {
                Log.d("Map cards", "Error: $errorMessage")
                Toast.makeText(context, "Bad internet signal!", Toast.LENGTH_SHORT).show()

            }

            maps != null -> {
                Text(text = "Maps view ✌️ ${maps!!.data.size}", color = Color.Black)
            }
        }
    }
}
