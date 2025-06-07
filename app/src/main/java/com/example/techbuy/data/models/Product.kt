package com.example.techbuy.data.models

data class Product(
    val id: Int,
    val name: String,
    val image: Int, // Assuming this is a Drawable resource ID
    val price: Double,
    val category: String,
    val description: String
)