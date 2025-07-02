package com.example.pawpal.uiScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pawpal.navigation.Screens
import com.example.pawpal.viewModel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier){
    val authview: AuthViewModel= viewModel()
    Box(
        modifier = Modifier.fillMaxSize()

    ){
        Button(onClick = {
            authview.signOut()
            navController.navigate(Screens.LoginScreen.route){
                popUpTo(Screens.ProfileScreen.route){
                    inclusive=true
                }
            }
        }) {
            Text("Log out")
        }
    }
}