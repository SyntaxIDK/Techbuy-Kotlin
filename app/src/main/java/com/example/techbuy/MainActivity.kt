package com.example.techbuy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.techbuy.navigation.TechBuyNavigation
import com.example.techbuy.ui.theme.AppTheme // Make sure to import the theme
import com.example.techbuy.utils.ThemePreferenceManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemePreferenceManager.init(applicationContext) // Initialize ThemePreferenceManager
        setContent {
            // Load theme preference, fallback to system setting if none is saved
            val initialDarkTheme = ThemePreferenceManager.loadTheme() ?: isSystemInDarkTheme()
            val isDarkTheme = remember { mutableStateOf(initialDarkTheme) }

            val toggleTheme: () -> Unit = {
                isDarkTheme.value = !isDarkTheme.value
                ThemePreferenceManager.saveTheme(isDarkTheme.value) // Save theme preference
            }
            AppTheme(darkTheme = isDarkTheme.value) {  // AppTheme is now correctly wrapped here
                val navController = rememberNavController()
                TechBuyNavigation(navController, toggleTheme)
            }
        }
    }
}