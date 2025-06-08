package com.example.techbuy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale // Added for Image scaling
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign // Added for text alignment
import androidx.compose.ui.unit.dp

@Composable
fun ProductCard(
    productName: String,
    productImage: Int,
    productPrice: Double,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Card should fill the width allocated by the grid cell
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Optional: add some elevation
    ) {
        Column(
            modifier = Modifier.padding(8.dp), // Reduced padding inside the card
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = productImage),
                contentDescription = productName,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f), // Square aspect ratio for the image
                contentScale = ContentScale.Crop // Crop image to fill bounds
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = productName,
                style = MaterialTheme.typography.titleSmall, // Adjusted for potentially less space
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${String.format("%.2f", productPrice)}",
                style = MaterialTheme.typography.bodyMedium, // Adjusted for potentially less space
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}