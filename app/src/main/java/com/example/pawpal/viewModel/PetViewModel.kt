package com.example.pawpal.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pawpal.models.Pet
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PetViewModel : ViewModel() {

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets = _pets.asStateFlow()

    private val _selectedPet = MutableStateFlow<Pet?>(null)
    val selectedPet = _selectedPet.asStateFlow()

    init {
        getPetList()
    }

    fun getPetList() {
        val db = Firebase.firestore
        db.collection("pets").addSnapshotListener { value, error ->
            if (error != null) {
                Log.e("Firestore", "Error fetching pets: ${error.message}")
                return@addSnapshotListener
            }

            val petList = value?.documents?.mapNotNull { doc ->
                val pet = doc.toObject(Pet::class.java)
                pet?.apply {
                    id = doc.id
                }
            } ?: emptyList()

            _pets.value = petList
        }
    }

    fun getPetById(id: String) {
        val db = Firebase.firestore
        db.collection("pets").document(id).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val pet = document.toObject(Pet::class.java)
                    _selectedPet.value = pet?.copy(id = document.id)
                } else {
                    Log.w("Firestore", "No pet found with ID: $id")
                }
            }
            .addOnFailureListener {
                Log.e("Firestore", "Error fetching pet by ID", it)
            }
    }

    fun addPetToFirebase(
        name: String,
        age: String,
        breed: String,
        imageUrl: String,
        gender: String
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        if (uid == null) {
            Log.e("AddPet", "User not logged in.")
            return
        }

        val db = Firebase.firestore

        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                val username = document.getString("name") ?: "Unknown"

                val newPet = Pet(
                    name = name,
                    age = age.toIntOrNull() ?: 0,
                    breed = breed,
                    imageUrl = imageUrl,
                    gender = gender,
                    owner = uid,
                    ownerName = username,
                    adopted = false
                )
                Firebase.firestore.collection("pets")
                    .add(newPet)
                    .addOnSuccessListener { documentReference ->
                        Log.d("AddPet", "Pet added with ID: ${documentReference.id}")
                        getPetList()
                    }
                    .addOnFailureListener { e ->
                        Log.e("AddPet", "Error adding new pet: ${e.message}", e)
                    }
            }
    }
}