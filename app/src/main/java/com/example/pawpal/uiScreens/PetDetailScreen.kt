package com.example.pawpal.uiScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pawpal.R
import com.example.pawpal.models.Message
import com.example.pawpal.viewModel.PetViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun PetDetailScreen(
    viewModel: PetViewModel = viewModel(),
    petId: String,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.getPetById(petId)
    }

    val pet = viewModel.selectedPet.collectAsState().value

    pet?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
        ) {
            Box {
                AsyncImage(
                    model = it.imageUrl,
                    contentDescription = "Pet Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                        .background(Color(0xFFFFA726))
                )

                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(36.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pet Details Card
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(it.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Text("(${it.breed})", color = Color.Gray)
                        }
                        AsyncImage(
                            model = it.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        PetInfoChip(it.gender, Color(0xFFD0F0C0))
                        PetInfoChip("${it.age} yrs.", Color(0xFFEBE7FD))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Red)
                        Text("New York", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Owner Info
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("${it.name} Owner", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(it.ownerName, fontWeight = FontWeight.Bold, color = Color(0xFF673AB7))
                            Text(
                                text = "Hi,I am ${it.ownerName}, ${it.name}’s Owner. I’m relocating and don’t have enough space to keep ${it.name}.",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        IconButton(onClick = {
                            val currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: return@IconButton
                            val chatId = listOf(currentUser, it.owner).sorted().joinToString("_")
                            val db = FirebaseDatabase.getInstance().getReference("chats")

                            db.child(chatId).get().addOnSuccessListener { snap ->
                                if (!snap.exists()) {
                                    db.child(chatId).push().setValue(
                                        Message(text = "Hi!", senderId = currentUser)
                                    )
                                }
                                navController.navigate("personal_chat_screen/$chatId")
                            }
                        }) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = Color(0xFF673AB7))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Adopt Button
            Button(
                onClick = { /* Handle Adopt Action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = if (it.adopted) "Already Adopted" else "Adopt ${it.name}",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PetInfoChip(text: String, background: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = background
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            fontSize = 12.sp
        )
    }
}

