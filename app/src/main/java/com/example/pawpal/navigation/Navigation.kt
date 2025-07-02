package com.example.pawpal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pawpal.authentication.authScreen.LoginScreen
import com.example.pawpal.authentication.authScreen.SignUpScreen
import com.example.pawpal.uiScreens.AddPetScreen
import com.example.pawpal.uiScreens.ChatScreen
import com.example.pawpal.uiScreens.MainScreen
import com.example.pawpal.uiScreens.PetDetailScreen
import com.example.pawpal.uiScreens.ProfileScreen
import com.example.pawpal.uiScreens.SplashScreen

@Composable
fun NavigationScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(Screens.RegisterScreen.route) {
            SignUpScreen {
                navController.navigate(Screens.LoginScreen.route)
            }
        }

        composable(Screens.LoginScreen.route) {
            LoginScreen(navController) {
                navController.navigate(Screens.RegisterScreen.route)
            }
        }

        composable(Screens.HomeScreen.route) {
            MainScreen()
        }

        composable(Screens.ChatScreen.route) { ChatScreen(
            navController
        ) }
        composable(Screens.ProfileScreen.route) { ProfileScreen(
            navController,
            Modifier
        ) }
        composable(Screens.AddPet.route) { AddPetScreen(navController) }


        composable(
            route = Screens.DetailScreen.route + "/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            PetDetailScreen(
                petId = petId,
                navController = navController
            )
        }
    }
}
