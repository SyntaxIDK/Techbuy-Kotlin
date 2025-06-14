package com.example.techbuy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.techbuy.data.DataSource // Import DataSource
import com.example.techbuy.data.models.User // Import User model

@Composable
fun ProfileScreen(navController: NavHostController) {
    val user = DataSource.getSampleUser() // Fetch sample user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Apply background color
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center content vertically for now
    ) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile Picture",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary // Optional: give it a color
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = user.name, // User.name field for username
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground // Explicitly set text color
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground // Explicitly set text color
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("wishlist") }
        ) {
            Text("My Wishlist")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("edit_profile") }
        ) {
            Text("Edit Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Non-functional for now */ }
        ) {
            Text("Delete Profile")
        }
    }
}

// The ProfileMenuItem composable can be kept if it's used elsewhere or removed if not.
// For this task, we are only changing its usage for "My Wishlist".
// If it's definitely not needed anywhere else, it can be removed in a separate cleanup task.
@Composable
fun ProfileMenuItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground, // Explicitly set text color for menu item
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Go to $text",
            tint = MaterialTheme.colorScheme.onBackground // Explicitly set icon tint for menu item
        )
    }
}
