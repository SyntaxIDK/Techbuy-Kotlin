package com.example.techbuy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.techbuy.R
import com.example.techbuy.ui.theme.AppTheme
import androidx.compose.ui.layout.ContentScale

@Composable
fun WelcomeScreen(navController: NavHostController) {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Background image with stretch and fill screen
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.welcome_cover_blur), // Background image
                    contentDescription = "Welcome Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // This will stretch the image to fill the screen without distorting it
                )

                // Dimmed overlay to make text stand out
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(colors = listOf(Color.Black.copy(alpha = 0.6f), Color.Transparent)))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Logo - Increased size
                    Image(
                        painter = painterResource(id = R.drawable.logo), // Your logo image
                        contentDescription = "TechBuy Logo",
                        modifier = Modifier
                            .size(180.dp) // Increased size of the logo
                            .padding(bottom = 32.dp)
                    )

                    // Welcome text
                    Text(
                        text = "Welcome to TechBuy",
                        style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    // Sign In Button
                    Button(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Sign In", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
                    }

                    // Sign Up Button
                    Button(
                        onClick = { navController.navigate("register") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Sign Up", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
                    }

                    // Social Media Links (Optional)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Login with Social Media",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navController = rememberNavController())
}