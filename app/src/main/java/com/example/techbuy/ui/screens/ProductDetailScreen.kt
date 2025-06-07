package com.example.techbuy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.techbuy.data.models.Product
import com.example.techbuy.ui.components.ProductCard

@Composable
fun ProductDetailScreen(navController: NavHostController) {
    // For now, we will simulate a product detail screen
    val product = Product(1, "iPhone 13", 999.99, com.example.techbuy.R.drawable.iphone)

    Column(modifier = Modifier.padding(16.dp)) {
        ProductCard(product.name, product.image)
        Text("Price: \$${product.price}")
        Button(onClick = { /* Navigate to Cart */ }) {
            Text("Add to Cart")
        }
    }
}