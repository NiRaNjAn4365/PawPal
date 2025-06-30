package com.example.pawpal.uiScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.pawpal.R
import com.example.pawpal.viewModel.PetViewModel

@Composable
fun PetDetailScreen(viewModel: PetViewModel=viewModel(),petId:String) {
    LaunchedEffect(Unit) {
        viewModel.getPetById(petId)
    }
    val selectPet=viewModel.selectedPet.collectAsState().value
    selectPet?.let {
        Column(modifier = Modifier.fillMaxSize()) {

            AsyncImage(
                model = it.imageUrl,
                contentDescription = "Pet Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                        )
                        Text(text = " (${it.breed})")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        Text(text = it.gender, color = Color.Green)
                        Spacer(modifier = Modifier.padding(start = 30.dp))
                        Text(text = "${it.age} years", color = Color.Magenta)
                    }
                }
            }


            Row(modifier = Modifier.padding(start = 12.dp, top = 10.dp)) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "profile image",
                    modifier = Modifier.width(50.dp)
                )
                Text(text = "Username", fontWeight = FontWeight.Medium)
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Chat, contentDescription = "Chat")
                }
            }

            Text(
                text = "Short description about the pet...",
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = { /* Handle adopt */ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Adopt Me!")
            }
        }
    }
}