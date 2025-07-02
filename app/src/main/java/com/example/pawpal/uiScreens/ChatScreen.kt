package com.example.pawpal.uiScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pawpal.models.ChatUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.firestore

@Composable
fun ChatScreen(navController: NavController) {
    val chatUsers = remember { mutableStateListOf<ChatUser>() }
    val currentUserId = Firebase.auth.currentUser?.uid ?: return

    val firestore = Firebase.firestore
    val chatsRef = FirebaseDatabase.getInstance().getReference("chats")

    LaunchedEffect(Unit) {
        chatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatUsers.clear()
                for (chatSnapshot in snapshot.children) {
                    val chatId = chatSnapshot.key ?: continue
                    val userIds = chatId.split("_")
                    if (userIds.contains(currentUserId)) {
                        val otherUserId = userIds.firstOrNull { it != currentUserId } ?: continue

                        firestore.collection("users").document(otherUserId).get()
                            .addOnSuccessListener { res ->
                                val name = res.getString("name") ?: "User"
                                val chatUser = ChatUser(uid = otherUserId, name = name)

                                if (chatUser !in chatUsers) {
                                    chatUsers.add(chatUser)
                                }
                            }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(chatUsers) { user ->
            ChatModule(name = user.name, uid = user.uid, navController = navController)
        }
    }
}
@Composable
fun ChatModule(name: String, uid: String, navController: NavController) {
    val currentUserId = Firebase.auth.currentUser?.uid ?: return
    val chatId = listOf(currentUserId, uid).sorted().joinToString("_")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("personal_chat_screen/$chatId")
            }
            .padding(12.dp)
    ) {
        Icon(Icons.Default.Person, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text("Tap to chat")
        }
    }
}