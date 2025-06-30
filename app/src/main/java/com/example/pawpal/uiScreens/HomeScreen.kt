package com.example.pawpal.uiScreens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pawpal.models.Pet
import com.example.pawpal.navigation.Screens
import com.example.pawpal.viewModel.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navcontroller: NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
Scaffold(topBar = {        CustomTopAppBar()
},) {innerPadding ->
    DogList(modifier = Modifier.padding(innerPadding),navcontroller)
}



    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "PawPal",
                color = Color.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Filled.Pets,
                    contentDescription = "Pets",
                    tint = Color.Black
                )
            }
        },
        actions = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}

@Composable
fun DogList(modifier: Modifier,navController: NavController,viewModel: PetViewModel=viewModel()) {

    val pets = viewModel.pets.collectAsStateWithLifecycle().value
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(8.dp)
    ) {
        items(pets) { pet ->
            DogCard(pet,navController)
        }
    }
}

@Composable
fun DogCard(pet: Pet,navController: NavController) {
    Card(
        onClick = {
            Log.d("NAVIGATION_DEBUG", "Navigating to: detail_screen/${pet.id}")
            navController.navigate("${Screens.DetailScreen.route}/${pet.id}")        },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth())
        {
            Log.d("Pet Image",""+pet.imageUrl)
            AsyncImage(
                model = pet.imageUrl,
                contentDescription = pet.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pet.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "(${pet.breed})",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = "${pet.gender} ${pet.age} years old",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
            )
        }
    }
}

