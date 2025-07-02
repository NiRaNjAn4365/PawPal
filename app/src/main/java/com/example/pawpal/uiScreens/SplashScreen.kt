package com.example.pawpal.uiScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawpal.navigation.Screens
import kotlinx.coroutines.delay

@Composable

fun SplashScreen(navController: NavController) {
        LaunchedEffect(Unit) {
                delay(3000)

                navController.navigate(Screens.LoginScreen.route) {
                        popUpTo(Screens.SplashScreen.route) { inclusive = true }
                }
        }

        Box(
                modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
        ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                                imageVector = Icons.Filled.Pets,
                                contentDescription = "Paw Icon",
                                modifier = Modifier.size(100.dp),
                                tint = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                                text = "PawPal",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                        )
                        Text(
                                text = "Find your new best friend 🐶🐱",
                                fontSize = 16.sp,
                                color = Color.White
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        CircularProgressIndicator(color = Color.White)
                }
        }
}