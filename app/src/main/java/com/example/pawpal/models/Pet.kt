package com.example.pawpal.models

data class Pet(
    var name: String = "",
    var age: Int = 0,
    var breed: String = "",
    var imageUrl: String = "",
    var gender: String = "",
    var owner: String = "",
    var adopted: Boolean = false,
    var id: String = "",
    val ownerName:String=""
)