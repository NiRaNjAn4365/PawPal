package com.example.pawpal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pawpal.uiScreens.AddPetScreen
import com.example.pawpal.uiScreens.ChatScreen
import com.example.pawpal.uiScreens.HomeScreen
import com.example.pawpal.uiScreens.PersonalChatScreen
import com.example.pawpal.uiScreens.PetDetailScreen
import com.example.pawpal.uiScreens.ProfileScreen

@Composable
fun MainNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") { HomeScreen(navController) }
        composable("chat") { ChatScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable(route=Screens.AddPet.route) {
            AddPetScreen(navController)
        }

        composable(
            route = Screens.DetailScreen.route + "/{petId}",
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            PetDetailScreen(

                petId = petId
            )
        }
        composable(            route= Screens.PersonalChatScreen.route
        ) {
            PersonalChatScreen()
        }
    }
}