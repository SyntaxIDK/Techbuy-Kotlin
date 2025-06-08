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
            val userThemePreference = remember { mutableStateOf(ThemePreferenceManager.loadTheme()) }
            val systemIsDark = isSystemInDarkTheme() // This will be reactive

            val toggleThemeCallback = {
                val currentPref = userThemePreference.value
                val newPref: Boolean? = when (currentPref) {
                    null -> !systemIsDark // Was System, override to opposite of current system.
                    true -> false          // Was Dark, set preference to Light.
                    false -> null         // Was Light, set preference to System (null).
                }
                userThemePreference.value = newPref
                ThemePreferenceManager.saveTheme(newPref)
            }

            AppTheme(userPreferenceIsDark = userThemePreference.value) {
                val navController = rememberNavController()
                // Pass the new toggleThemeCallback to TechBuyNavigation
                TechBuyNavigation(navController, toggleThemeCallback)
            }
        }
    }
}