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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
                    items(cartItems, key = { "${it.product.id}-${it.selectedColor}-${it.selectedRom}" }) { item ->
                        CartListItem(
                            item = item,
                            onRemoveItem = {
                                DataSource.removeCartItem(item.product.id, item.selectedColor, item.selectedRom)
                                updateCartItems()
                            },
                            onIncreaseQuantity = {
                                DataSource.addToCart(item.product, 1, item.selectedColor, item.selectedRom)
                                updateCartItems()
                            },
                            onDecreaseQuantity = {
                                DataSource.decreaseCartItemQuantity(item.product.id, item.selectedColor, item.selectedRom)
                                updateCartItems()
                            }
                        )
                        Divider()
                    }
                }

                // Payment Buttons Column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { /*TODO: Implement PayPal payment*/ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Pay with PayPal")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { /*TODO: Implement Google Pay payment*/ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Pay with Google")
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
                    onClick = { navController.navigate("checkout") },
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
            .padding(vertical = 12.dp), // Increased padding for better spacing
        verticalAlignment = Alignment.Top // Align items to the top for better visual hierarchy
    ) {
        // Product Image
        AsyncImage(
            model = item.product.imageUrl,
            contentDescription = item.product.name,
            modifier = Modifier
                .size(100.dp) // Fixed size for the image
                .clip(MaterialTheme.shapes.medium), // Rounded corners
            contentScale = ContentScale.Crop // Crop image to fit
        )

        Spacer(modifier = Modifier.width(16.dp)) // Space between image and details

        // Product Details and Controls
        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, style = MaterialTheme.typography.titleLarge) // Larger title
            Spacer(modifier = Modifier.height(4.dp))

            // Selected Attributes
            item.selectedColor?.let {
                Text("Color: $it", style = MaterialTheme.typography.bodyMedium)
            }
            item.selectedRom?.let {
                Text("Storage: $it", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                "Price: \$${String.format("%.2f", item.product.price)}",
                style = MaterialTheme.typography.bodyLarge // Slightly larger price text
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Quantity Controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecreaseQuantity, modifier = Modifier.size(36.dp)) { // Smaller IconButtons
                    Icon(Icons.Filled.Remove, contentDescription = "Decrease Quantity")
                }
                Text(
                    item.quantity.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = onIncreaseQuantity, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Filled.Add, contentDescription = "Increase Quantity")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Subtotal: \$${String.format("%.2f", item.product.price * item.quantity)}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) // Bold subtotal
            )
        }

        // Remove Button - aligned to the side
        IconButton(
            onClick = onRemoveItem,
            modifier = Modifier.align(Alignment.Top) // Align to top of the Row
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "Remove Item")
        }
    }
}