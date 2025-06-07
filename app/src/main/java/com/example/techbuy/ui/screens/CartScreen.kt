package com.example.techbuy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.techbuy.data.models.Product
import com.example.techbuy.viewmodels.CartViewModel

@Composable
fun CartScreen(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.cartItems.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cart Screen", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        if (cartItems.isEmpty()) {
            Text("Your cart is empty")
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { product ->
                    CartItemRow(product = product)
                }
            }
            Text(
                text = "Total: \$${cartItems.sumOf { it.price }}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Button(
                onClick = { /* Proceed to checkout */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Checkout")
            }
        }
    }
}

@Composable
fun CartItemRow(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(product.name, style = MaterialTheme.typography.bodyLarge)
        Text("\$${product.price}", style = MaterialTheme.typography.bodyMedium)
    }
}