package com.example.pawpal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pawpal.uiScreens.MainScreen
import com.example.pawpal.uiScreens.SplashScreen

@Composable
fun NavigationScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screens.HomeScreen.route) {
            MainScreen()
        }
    }
}