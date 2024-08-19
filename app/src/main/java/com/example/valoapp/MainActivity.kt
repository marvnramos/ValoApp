package com.example.valoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.valoapp.ui.view.HomeView
import com.example.valoapp.ui.view.theme.ValoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValoAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeView()
                }
            }
        }
    }
}
