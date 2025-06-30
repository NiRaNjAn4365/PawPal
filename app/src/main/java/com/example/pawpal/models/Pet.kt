package com.example.pawpal.models

data class Pet(
    val imageUrl:String="",
    val name:String="",
    val gender:String="",
    val age: Int=0,
    val breed:String="",
    var id: String = ""

)