package com.example.pawpal.uiScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pawpal.R
import com.example.pawpal.navigation.Screens

@Composable
fun ChatScreen(navController: NavController, modifier: Modifier) {
    val names = listOf("John Deep", "Harry Troop", "Gary Oak", "Alice Wonderland", "Emma James")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 6.dp, vertical = 38.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(names) { name ->
            ChatModule(name,navController)
        }
    }
}

@Composable
fun ChatModule(name: String,navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = {
                navController.navigate(Screens.PersonalChatScreen.route)
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Start a conversation with $name"
                , fontWeight = FontWeight.W200

            )
        }
    }
}
