package com.example.techbuy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme // Added for typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment // Added for alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
// Removed com.example.techbuy.R as it's not directly used in this snippet, assuming productImage is a drawable resource ID

@Composable
fun ProductCard(
    productName: String,
    productImage: Int,
    productPrice: Double, // Added productPrice parameter
    onClick: () -> Unit
) {
    Card(modifier = Modifier.padding(8.dp).clickable { onClick() }) {
        Column(
            modifier = Modifier.padding(16.dp), // Increased padding for better spacing
            horizontalAlignment = Alignment.CenterHorizontally // Center content
        ) {
            Image(
                painter = painterResource(id = productImage),
                contentDescription = productName, // Use productName for better accessibility
                modifier = Modifier.height(120.dp) // Give image a fixed height
            )
            Spacer(modifier = Modifier.height(8.dp)) // Spacer for visual separation
            Text(
                text = productName,
                style = MaterialTheme.typography.titleMedium // Use a slightly larger style for name
            )
            Spacer(modifier = Modifier.height(4.dp)) // Spacer
            Text(
                text = "$${String.format("%.2f", productPrice)}", // Display formatted price
                style = MaterialTheme.typography.bodyLarge // Use a clear style for price
            )
        }
    }
}