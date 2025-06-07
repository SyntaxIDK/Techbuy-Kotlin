package com.example.techbuy.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
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
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProductDetailScreen(
                navController = navController,
                productId = backStackEntry.arguments?.getString("productId")
            )
        }
        composable("cart") { CartScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("search") { SearchScreen(navController) }
    }
}