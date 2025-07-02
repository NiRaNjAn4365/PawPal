package com.example.pawpal.uiScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pawpal.navigation.Screens

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = listOf(Screens.HomeScreen.route, Screens.ChatScreen.route, Screens.ProfileScreen.route).contains(currentRoute)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.HomeScreen.route) { HomeScreen(navController, Modifier) }
            composable(Screens.ChatScreen.route) { ChatScreen(navController, Modifier) }
            composable(Screens.ProfileScreen.route) { ProfileScreen(navController, Modifier) }
            composable(Screens.AddPet.route) { AddPetScreen(navController) }

            composable(Screens.PersonalChatScreen.route) { PersonalChatScreen() }

            composable(
                route = Screens.DetailScreen.route + "/{petId}",
                arguments = listOf(navArgument("petId") { type = NavType.StringType })
            ) { backStackEntry ->
                val petId = backStackEntry.arguments?.getString("petId") ?: ""
                PetDetailScreen(petId = petId)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem(Screens.HomeScreen.route, Icons.Default.Home, "Home"),
        BottomNavItem(Screens.AddPet.route, Icons.Default.Add, "Add Pet"),
        BottomNavItem(Screens.ChatScreen.route, Icons.Default.Chat, "Chat"),
        BottomNavItem(Screens.ProfileScreen.route, Icons.Default.Person, "Profile")
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screens.HomeScreen.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)