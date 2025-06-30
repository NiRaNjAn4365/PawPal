package com.example.pawpal.navigation

sealed class Screens(val route:String) {
    object SplashScreen: Screens("splash_screen")
    object HomeScreen: Screens("home_screen")
    object DetailScreen: Screens("detail_screen")
    object AddPet: Screens("add_pet")
    object LoginScreen: Screens("login_screen")
    object PersonalChatScreen: Screens("personal_chat_screen")
    object RegisterScreen:Screens("register_screen")
    object ProfileScreen: Screens("profile_screen")
    object ChatScreen: Screens("chat_screen")
}