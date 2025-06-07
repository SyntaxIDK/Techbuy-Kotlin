package com.example.techbuy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Column {
        Text("Home Screen")
        Button(onClick = { navController.navigate("products") }) {
            Text("View Products")
        }
    }
}