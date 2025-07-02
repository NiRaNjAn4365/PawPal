package com.example.pawpal.uiScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pawpal.R
import com.example.pawpal.models.User
import com.example.pawpal.navigation.Screens
import com.example.pawpal.viewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier = Modifier) {
    val auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid ?: return
    val authViewModel: AuthViewModel = viewModel()

    var userDetails by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(uid) {
        Firebase.firestore.collection("users").document(uid).get()
            .addOnSuccessListener { res ->
                if (res.exists()) {
                    userDetails = res.toObject(User::class.java)
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F8))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Profile Picture with Edit Button
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(80.dp),
                    tint = Color.DarkGray
                )
            }

            Surface(
                shape = CircleShape,
                color = Color(0xFF1976D2),
                modifier = Modifier
                    .offset(x = (-8).dp, y = (-8).dp)
                    .size(28.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userDetails?.name ?: "No Name",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Surface(
            color = Color(0xFFE3F2FD),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = userDetails?.email ?: "no@email.com",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color(0xFF1976D2)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Options Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                ProfileOption("Edit Profile", R.drawable.ic_edit)
                ProfileOption("Add Pin", R.drawable.ic_pin)
                ProfileOption("Settings", R.drawable.ic_settings)
                ProfileOption("Invite a friend", R.drawable.ic_invite)
                Divider(color = Color.LightGray)
                ProfileOption(
                    text = "Logout",
                    icon = R.drawable.ic_logout,
                    iconTint = Color.Red,
                    textColor = Color.Red
                ) {
                    authViewModel.signOut()
                    navController.navigate(Screens.LoginScreen.route) {
                        popUpTo(Screens.HomeScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileOption(
    text: String,
    icon: Int,
    iconTint: Color = Color.Black,
    textColor: Color = Color.Black,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        headlineContent = {
            Text(text = text, color = textColor, fontSize = 16.sp)
        },
        leadingContent = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)  // ⬅️ Reduced icon size here
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)  // ⬅️ Match trailing arrow size
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 8.dp, vertical = 4.dp) // spacing
    )
}