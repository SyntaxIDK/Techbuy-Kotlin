package com.example.techbuy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.techbuy.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.value = _cartItems.value + product
    }
}
