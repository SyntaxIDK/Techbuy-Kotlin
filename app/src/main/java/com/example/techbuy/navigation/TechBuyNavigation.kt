package com.example.techbuy.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType // Added import
import androidx.navigation.navArgument // Added import
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable // Import for animated composable
import com.example.techbuy.ui.screens.*
import com.example.techbuy.ui.screens.EditProfileScreen // Added import
import com.example.techbuy.ui.screens.WishlistScreen
import com.example.techbuy.ui.screens.CheckoutScreen
import com.example.techbuy.ui.screens.OrderConfirmationScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TechBuyNavigation(navController: NavHostController, toggleTheme: () -> Unit) {
    SharedTransitionLayout {
        AnimatedNavHost(navController = navController, startDestination = "welcome") {
            composable(
                "welcome",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { WelcomeScreen(navController) }
        composable(
            "login",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { LoginScreen(navController) }
        composable(
            "register",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { RegisterScreen(navController) }
        composable(
            route = "home?showCategorySelector={showCategorySelector}",
            arguments = listOf(navArgument("showCategorySelector") {
                type = NavType.BoolType
                defaultValue = false
            }),
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { backStackEntry ->
            val showSelector = backStackEntry.arguments?.getBoolean("showCategorySelector") ?: false
            HomeScreen(
                navController = navController,
                showCategorySelector = showSelector,
                toggleTheme = toggleTheme,
                animatedVisibilityScope = this@composable // Pass scope
            )
        }
        composable(
            "products",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { ProductsScreen(navController = navController, animatedVisibilityScope = this@composable) } // Pass scope
        composable(
            route = "product_detail/{productId}", // Changed route
            arguments = listOf(navArgument("productId") { type = NavType.IntType }), // Added arguments
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { backStackEntry ->
            // Retrieve productId.
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                 ProductDetailScreen(
                    navController = navController,
                    productId = productId,
                    animatedVisibilityScope = this@composable // Pass the scope
                )
            } else {
                // Handle error: productId not found, perhaps navigate back or show error
            }
        }
        composable(
            "cart",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { CartScreen(navController) }
        composable(
            "profile",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { ProfileScreen(navController) }
        composable(
            "edit_profile",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { EditProfileScreen(navController) } // Added route
        composable(
            "search",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { SearchScreen(navController) }
        composable(
            "wishlist",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { WishlistScreen(navController) }
        composable(
            "checkout",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { CheckoutScreen(navController) }
        composable(
            "order_confirmation",
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { OrderConfirmationScreen(navController) }
    }
    }
}
