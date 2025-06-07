package com.example.techbuy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // For LazyListScope (LazyRow, LazyColumn)
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // For LazyGridScope (LazyVerticalGrid)
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge // Added import
import androidx.compose.material3.BadgedBox // Added import
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.techbuy.R
import com.example.techbuy.data.DataSource
import com.example.techbuy.data.models.Product // Ensure this is imported
import com.example.techbuy.ui.components.ProductCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val cartItemCount = DataSource.getCartItemCount() // Get cart item count

    // Added "cart" and reordered. Assuming "home_route", "categories_route", "cart", "profile_route"
    val bottomNavItems = listOf(
        Triple("Home", Icons.Filled.Home, "home_route"),
        Triple("Categories", Icons.Filled.List, "categories_route"),
        Triple("Cart", Icons.Filled.ShoppingCart, "cart"),
        Triple("Profile", Icons.Filled.Person, "profile_route")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Menu Item 1")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Menu Item 2")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Menu Item 3")
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TechBuy") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("cart") }) {
                            BadgedBox(badge = {
                                if (cartItemCount > 0) {
                                    Badge { Text(cartItemCount.toString()) }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = "Shopping Cart"
                                )
                            }
                        }
                        IconButton(onClick = { navController.navigate("profile_route") }) { // Assuming profile_route for consistency
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "User Profile"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(item.third) // Ensure this uses the route
                            },
                            label = { Text(item.first) },
                            icon = {
                                val currentCartItemCount = DataSource.getCartItemCount() // Fetch count here
                                BadgedBox(badge = {
                                    if (item.third == "cart" && currentCartItemCount > 0) {
                                        Badge { Text(currentCartItemCount.toString()) }
                                    }
                                }) {
                                    Icon(
                                        imageVector = item.second, // This should be ShoppingCart icon for cart
                                        contentDescription = item.first
                                    )
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            val categories = DataSource.getProductCategories()
            val products = DataSource.getProducts()

            Column(modifier = Modifier.padding(innerPadding)) {
                ProductCategoriesRow(
                    categories = categories,
                    onCategoryClick = { categoryName ->
                        // TODO: Handle category click: e.g., navigate to a filtered product list
                    }
                )
                ProductGrid(
                    products = products,
                    categories = categories,
                    onProductClick = { product ->
                        // Navigate to product detail screen with product ID
                        navController.navigate("product_detail/${product.id}")
                    },
                    onCategoryClick = { categoryName ->
                        // TODO: Handle category click: e.g., navigate to a filtered product list
                    }
                )
            }
        }
    }
}

@Composable
private fun ProductCategoriesRow(categories: List<String>, onCategoryClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category -> // This items is from androidx.compose.foundation.lazy.LazyListScope
            Card(
                modifier = Modifier.clickable { onCategoryClick(category) },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun HomeBanner() {
    Image(
        painter = painterResource(id = R.drawable.home_banner),
        contentDescription = "Promotional Banner",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 8.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ProductGrid(
    products: List<Product>,
    categories: List<String>,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            ProductCategoriesRow(categories = categories, onCategoryClick = onCategoryClick)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            HomeBanner()
        }
        // Ensure this items is from androidx.compose.foundation.lazy.grid.LazyGridScope
        items(products, key = { product -> product.id }) { product ->
            ProductCard(
                productName = product.name,
                productImage = product.image,
                productPrice = product.price, // Added productPrice
                onClick = { onProductClick(product) }
            )
        }
    }
}