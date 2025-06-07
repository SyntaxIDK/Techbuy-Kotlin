package com.example.techbuy.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavBar() {
    BottomAppBar {
        IconButton(onClick = { /* Navigate to Home */ }) {
            Icon(imageVector = Icons.Filled.Home, contentDescription = null)
        }
        IconButton(onClick = { /* Navigate to Cart */ }) {
            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
        }
        IconButton(onClick = { /* Navigate to Profile */ }) {
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null)
        }
    }
}