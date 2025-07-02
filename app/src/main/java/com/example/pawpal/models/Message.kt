package com.example.pawpal.models

data class Message(
    val text: String = "",
    val senderId: String = ""
) {
    val isFromCurrentUser: Boolean
        get() = senderId == com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
}