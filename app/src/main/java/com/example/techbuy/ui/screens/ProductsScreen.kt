package com.example.techbuy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // Important: use this items for LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController // Keep this if ProductCard's onClick needs it
import com.example.techbuy.data.DataSource
import com.example.techbuy.ui.components.ProductCard

@Composable
fun ProductsScreen(navController: NavHostController) {
    val products = DataSource.getProducts()

    Column(modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)) { // Adjusted padding for Column
        Text(
            "Products List",
            modifier = Modifier.padding(start = 8.dp, bottom = 16.dp) // Padding for the title
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),      // Padding around the entire grid content
            verticalArrangement = Arrangement.spacedBy(8.dp),   // Spacing between rows
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between columns
        ) {
            items(products) { product -> // Using items from androidx.compose.foundation.lazy.grid.items
                ProductCard(
                    productName = product.name,
                    productImage = product.image,
                    productPrice = product.price,
                    onClick = {
                        // Navigate to product detail screen
                        navController.navigate("product_detail/${product.id}")
                    }
                )
            }
        }
    }
}