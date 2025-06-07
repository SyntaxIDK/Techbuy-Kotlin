package com.example.techbuy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.techbuy.data.DataSource
import com.example.techbuy.ui.components.ProductCard

@Composable
fun ProductsScreen(navController: NavHostController) {
    val products = DataSource.getProducts()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Products List")
        LazyColumn {
            items(products) { product ->
                ProductCard(product.name, product.image, onClick = {}) // onClick added
            }
        }
    }
}