package com.example.techbuy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.techbuy.data.DataSource
import com.example.techbuy.data.models.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    var cartItems by remember { mutableStateOf(DataSource.getCartItems()) }

    fun updateCartItems() {
        cartItems = DataSource.getCartItems()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart") },
                actions = {
                    IconButton(onClick = {
                        DataSource.clearCart()
                        updateCartItems()
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Clear Cart")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty.", style = MaterialTheme.typography.headlineSmall)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(cartItems, key = { it.product.id }) { item ->
                        CartListItem(
                            item = item,
                            onRemoveItem = {
                                DataSource.removeCartItem(item.product.id)
                                updateCartItems()
                            },
                            onIncreaseQuantity = {
                                DataSource.addToCart(item.product, 1) // Adds 1 to existing
                                updateCartItems()
                            },
                            onDecreaseQuantity = {
                                DataSource.decreaseCartItemQuantity(item.product.id)
                                updateCartItems()
                            }
                        )
                        Divider()
                    }
                }

                val totalPrice = cartItems.sumOf { it.product.price * it.quantity }
                Text(
                    text = "Total: \$${String.format("%.2f", totalPrice)}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.End)
                )
                Button(
                    onClick = { /* TODO: Navigate to checkout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Proceed to Checkout")
                }
            }
        }
    }
}

@Composable
fun CartListItem(
    item: CartItem,
    onRemoveItem: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, style = MaterialTheme.typography.titleMedium)
            Text(
                "Price: \$${String.format("%.2f", item.product.price)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Item Total: \$${String.format("%.2f", item.product.price * item.quantity)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecreaseQuantity) {
                Icon(Icons.Filled.Remove, contentDescription = "Decrease Quantity")
            }
            Text(item.quantity.toString(), style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onIncreaseQuantity) {
                Icon(Icons.Filled.Add, contentDescription = "Increase Quantity")
            }
            IconButton(onClick = onRemoveItem) {
                Icon(Icons.Filled.Delete, contentDescription = "Remove Item")
            }
        }
    }
}