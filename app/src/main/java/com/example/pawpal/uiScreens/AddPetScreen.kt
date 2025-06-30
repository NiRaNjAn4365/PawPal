package com.example.pawpal.uiScreens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.pawpal.network.uploadToImgBB
import com.example.pawpal.viewModel.PetViewModel


@Composable
fun AddPetScreen(navController: NavController,viewModel: PetViewModel = viewModel()){
  AddPet { name,breed,age,gender,imageUrl->
      viewModel.addPetToFirebase(
          name =name,
          age=age,
          breed=breed,
          imageUrl = imageUrl,
          gender=gender
      )
  }
}

@Composable
fun AddPet(
    onAddClick: (String, String, String, String,String) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val breed = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }
    val context= LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                uploadToImgBB(context, uri) { directUrl ->
                    imageUrl.value = directUrl
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Add New Pet", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Pet Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = breed.value,
            onValueChange = { breed.value = it },
            label = { Text("Breed") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age.value,
            onValueChange = { age.value = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = gender.value,
            onValueChange = { gender.value = it },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pick Image")
        }

        imageUrl.value.let { uri ->
            Spacer(modifier = Modifier.height(12.dp))
            AsyncImage(
                model = uri,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(name.value==""|| breed.value==""||age.value==""||gender.value==""||imageUrl.value==""){
                    Toast.makeText(context,"Please Specify all the fields here", Toast.LENGTH_SHORT).show()
                }else {

                    onAddClick(
                        name.value.trim(),
                        breed.value.trim(),
                        age.value.trim(),
                        gender.value.trim(),
                        imageUrl.value
                    )

                    name.value=""
                    breed.value=""
                    age.value=""
                    gender.value=""
                    imageUrl.value=""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Pet")
        }
    }
}

