package com.example.valoapp.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.valoapp.ui.view.components.NavigationBarSample
import com.example.valoapp.ui.view.screens.HomeScreen
import com.example.valoapp.ui.view.screens.MapsScreen

@Composable
fun MainView() {
    val navController = rememberNavController()
    Column(modifier = Modifier.fillMaxSize()) {

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.weight(1f)
        ) {
            composable("home") { HomeScreen() }
            composable("maps") { MapsScreen() }
        }

        NavigationBarSample(navController = navController)
    }
}

@Preview
@Composable
fun PreviewMainView() {
    MainView()
}
