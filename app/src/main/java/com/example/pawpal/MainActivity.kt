package com.example.pawpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.pawpal.navigation.NavigationScreen
import com.example.pawpal.ui.theme.PawPalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PawPalTheme {
                val controller= rememberNavController()
                NavigationScreen(controller)
            }
        }
    }
}
