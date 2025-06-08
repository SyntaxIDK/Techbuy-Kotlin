package com.example.techbuy.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Badge // Added import
import androidx.compose.material3.BadgedBox // Added import
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
// Text import might not be needed if only icons are used, but keeping it doesn't hurt.
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController // Added import
import com.example.techbuy.data.DataSource // Added import for DataSource

@Composable
fun BottomNavBar(navController: NavHostController) { // Added navController parameter
    val cartItemCount = DataSource.getCartItemCount() // Get cart item count

    BottomAppBar {
        IconButton(onClick = { navController.navigate("home_route") }) { // Assuming "home_route"
            Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
        }
        IconButton(onClick = { navController.navigate("products") }) {
            Icon(imageVector = Icons.Filled.Store, contentDescription = "Shop")
        }
        IconButton(onClick = { navController.navigate("cart") }) { // Updated for cart
            BadgedBox(badge = {
                if (cartItemCount > 0) {
                    Badge { Text(cartItemCount.toString()) }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Shopping Cart" // Changed to "Shopping Cart"
                )
            }
        }
        IconButton(onClick = { navController.navigate("profile_route") }) { // Assuming "profile_route"
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile")
        }
    }
}