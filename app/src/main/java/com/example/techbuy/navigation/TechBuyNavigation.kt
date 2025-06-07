package com.example.techbuy.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType // Added import
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument // Added import
import com.example.techbuy.ui.screens.*

@Composable
fun TechBuyNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {WelcomeScreen(navController)}
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("products") { ProductsScreen(navController) }
        composable(
            route = "product_detail/{productId}", // Changed route
            arguments = listOf(navArgument("productId") { type = NavType.IntType }) // Added arguments
        ) { backStackEntry ->
            // Retrieve productId. ProductDetailScreen will be updated later to accept it.
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                // For now, ProductDetailScreen still expects navController.
                // This will be reconciled when ProductDetailScreen is updated.
                // The ideal way would be to pass productId directly: ProductDetailScreen(navController, productId)
                // or ProductDetailScreen(productId = productId, navController = navController)
                // For this step, we ensure the argument is extracted. The next step will modify ProductDetailScreen.
                 ProductDetailScreen(navController = navController, productId = productId)
            } else {
                // Handle error: productId not found, perhaps navigate back or show error
            }
        }
        composable("cart") { CartScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("search") { SearchScreen(navController) }
    }
}