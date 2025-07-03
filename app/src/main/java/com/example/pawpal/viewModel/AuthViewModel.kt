package com.example.pawpal.viewModel

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _authState = MutableLiveData<AuthState>()
    val authState = _authState

    init {
        checkStatus()
    }

    fun checkStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.UnAuthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun signUpUser(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.ErrorMessage("Please fill in all fields.")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    val userMap = mapOf(
                        "name" to name,
                        "email" to email,
                        "uid" to userId
                    )

                    firestore.collection("users")
                        .document(userId)
                        .set(userMap)
                        .addOnSuccessListener {
                            _authState.value = AuthState.Authenticated
                        }
                        .addOnFailureListener { e ->
                            _authState.value =
                                AuthState.ErrorMessage("Firestore error: ${e.message}")
                        }
                } else {
                    _authState.value = AuthState.ErrorMessage(
                        task.exception?.localizedMessage ?: "Signup failed"
                    )
                }
            }
    }

    fun signInUser(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.ErrorMessage("Please enter both email and password.")
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.ErrorMessage(
                        task.exception?.localizedMessage ?: "Login failed"
                    )
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    object UnAuthenticated : AuthState()
    object Loading : AuthState()
    data class ErrorMessage(val data: String) : AuthState()
}