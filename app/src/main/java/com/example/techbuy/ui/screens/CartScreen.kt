package com.example.techbuy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun CartScreen(navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cart Screen")
        // Example cart item
        Text("Item: iPhone 13")
        Text("Price: \$999.99")
        Button(onClick = { /* Proceed to checkout */ }) {
            Text("Checkout")
        }
    }
}