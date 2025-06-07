package com.example.techbuy.data.models

data class CartItem(
    val product: Product,
    var quantity: Int,
    val selectedColor: String?, // Added
    val selectedRom: String?    // Added
)
