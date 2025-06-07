package com.example.techbuy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.techbuy.navigation.TechBuyNavigation
import com.example.techbuy.ui.theme.AppTheme // Make sure to import the theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {  // AppTheme is now correctly wrapped here
                val navController = rememberNavController()
                TechBuyNavigation(navController)
            }
        }
    }
}