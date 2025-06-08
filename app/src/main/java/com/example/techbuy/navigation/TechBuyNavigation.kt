package com.example.techbuy.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType // Added import
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument // Added import
import com.example.techbuy.ui.screens.*
import com.example.techbuy.ui.screens.EditProfileScreen // Added import
import com.example.techbuy.ui.screens.WishlistScreen
import com.example.techbuy.ui.screens.CheckoutScreen
import com.example.techbuy.ui.screens.OrderConfirmationScreen

@Composable
fun TechBuyNavigation(navController: NavHostController, toggleTheme: () -> Unit) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {WelcomeScreen(navController)}
        composable(
            "login",
            exitTransition = {
                scaleOut(animationSpec = tween(durationMillis = 300)) +
                        fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable(
            route = "home?showCategorySelector={showCategorySelector}",
            arguments = listOf(navArgument("showCategorySelector") {
                type = NavType.BoolType
                defaultValue = false
            }),
            enterTransition = {
                scaleIn(animationSpec = tween(durationMillis = 300, delayMillis = 150)) +
                        fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 150))
            }
        ) { backStackEntry ->
            val showSelector = backStackEntry.arguments?.getBoolean("showCategorySelector") ?: false
            HomeScreen(navController = navController, showCategorySelector = showSelector, toggleTheme = toggleTheme)
        }
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
        composable("edit_profile") { EditProfileScreen(navController) } // Added route
        composable("search") { SearchScreen(navController) }
        composable("wishlist") { WishlistScreen(navController) }
        composable("checkout") { CheckoutScreen(navController) }
        composable("order_confirmation") { OrderConfirmationScreen(navController) }
    }
}
