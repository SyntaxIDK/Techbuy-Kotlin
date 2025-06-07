package com.example.techbuy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.techbuy.data.DataSource
import com.example.techbuy.viewmodels.CartViewModel
import android.widget.Toast


@Composable
fun ProductDetailScreen(navController: NavHostController, productId: String?) {
    val product = productId?.toIntOrNull()?.let { id ->
        DataSource.getProducts().find { it.id == id }
    }
    val cartViewModel: CartViewModel = viewModel()
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Center content like "Product not found"
    ) {
        if (product != null) {
            // Product Image
            Image(
                painter = painterResource(id = product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Product Name
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Start) // Align text to the start
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product Price
            Text(
                text = "Price: \$${product.price}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start) // Align text to the start
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes button to the bottom

            // Add to Cart Button
            Button(
                onClick = {
                    cartViewModel.addToCart(product)
                    Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Add to Cart")
            }
        } else {
            Text("Product not found", style = MaterialTheme.typography.headlineSmall)
        }
    }
}