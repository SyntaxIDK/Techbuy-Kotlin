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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items // For LazyListScope (LazyRow, LazyColumn)
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // For LazyGridScope (LazyVerticalGrid)
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge // Added import
import androidx.compose.material3.BadgedBox // Added import
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.techbuy.R
import com.example.techbuy.data.DataSource
import com.example.techbuy.data.models.Product // Ensure this is imported
import com.example.techbuy.ui.components.ProductCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    showCategorySelector: Boolean = false,
    toggleTheme: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope // Added parameter
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val cartItemCount = DataSource.getCartItemCount() // Get cart item count

    LaunchedEffect(showCategorySelector) {
        if (showCategorySelector) {
            showDialog = true
            // Further dialog management will be handled by its onDismiss or other mechanisms
        }
    }

    if (showDialog) {
        CategorySelectionDialog(
            onCategorySelected = { categoryName ->
                selectedCategory = categoryName
            },
            onDismiss = { showDialog = false }
        )
    }

    // Added "cart" and reordered. Assuming "home_route", "categories_route", "cart", "profile_route"
    val bottomNavItems = listOf(
        Triple("Home", Icons.Filled.Home, "home"), // Changed
        Triple("Categories", Icons.Filled.List, "home"), // Changed
        Triple("Cart", Icons.Filled.ShoppingCart, "cart"), // Stays the same
        Triple("Profile", Icons.Filled.Person, "profile") // Changed
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Inside ModalDrawerSheet
                Column(
                    modifier = Modifier.padding(16.dp), // Keep padding or adjust as needed
                    horizontalAlignment = Alignment.CenterHorizontally // Center header content
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kaveesha",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "kaveesha@techbuy.com",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider() // New Material 3 Divider
                    Spacer(modifier = Modifier.height(16.dp))

                    // Define menu items
                    val menuItems = listOf(
                        Triple("My Profile", Icons.Filled.Person, "profile_drawer_item"), // Added a unique key for selection state
                        Triple("Cart", Icons.Filled.ShoppingCart, "cart_drawer_item"),
                        Triple("Wishlist", Icons.Filled.FavoriteBorder, "wishlist_drawer_item")
                    )

                    // State for selected item in drawer (optional, but good for NavigationDrawerItem)
                    var selectedDrawerItemKey by remember { mutableStateOf<String?>(null) }

                    menuItems.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.second, contentDescription = item.first) },
                            label = { Text(item.first) },
                            selected = item.third == selectedDrawerItemKey, // Manage selection state
                            onClick = {
                                selectedDrawerItemKey = item.third
                                scope.launch { drawerState.close() } // Close drawer on any item click

                                when (item.first) { // Assuming item.first is "My Profile", "Cart", or "Wishlist"
                                    "My Profile" -> {
                                        navController.navigate("profile") {
                                            popUpTo("home")
                                            launchSingleTop = true
                                        }
                                    }
                                    "Cart" -> {
                                        navController.navigate("cart") {
                                            popUpTo("home")
                                            launchSingleTop = true
                                        }
                                    }
                                    "Wishlist" -> {
                                        navController.navigate("wishlist") {
                                            popUpTo("home") // Or the current route of HomeScreen if different
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                        Spacer(modifier = Modifier.height(4.dp)) // Optional: space between items
                    }
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
                        IconButton(onClick = { navController.navigate("profile") }) { // Corrected to "profile"
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "User Profile"
                            )
                        }
                        IconButton(onClick = toggleTheme) {
                            Icon(
                                imageVector = Icons.Filled.BrightnessMedium,
                                contentDescription = "Toggle Theme"
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
                                if (item.first == "Categories") { // Check if the item is "Categories" by its name (item.first)
                                    navController.navigate("home?showCategorySelector=true") {
                                        popUpTo("home") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(item.third) {
                                        if (item.third != "home") {
                                             popUpTo("home")
                                        }
                                        launchSingleTop = true
                                    }
                                }
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
            val allProducts = DataSource.getProducts()
            val products = remember(selectedCategory, allProducts) {
                when (selectedCategory) {
                    "Smartphones" -> allProducts.filter { it.name.contains("iPhone", ignoreCase = true) }
                    "Laptops" -> allProducts.filter { it.name.contains("MacBook", ignoreCase = true) }
                    else -> allProducts
                }
            }

            Column(modifier = Modifier.padding(innerPadding)) {
                ProductCategoriesRow(
                    categories = categories,
                    selectedCategory = selectedCategory, // Add this
                    onCategoryClick = { categoryName ->
                        selectedCategory = if (selectedCategory == categoryName) null else categoryName
                    }
                )
                ProductGrid(
                    products = products,
                    categories = categories, // categories is still available here
                    onProductClick = { product ->
                        // Navigate to product detail screen with product ID
                        navController.navigate("product_detail/${product.id}")
                    },
                    animatedVisibilityScope = animatedVisibilityScope // Pass scope
                    // Removed onCategoryClick from ProductGrid call site
                )
            }
        }
    }
}

@Composable
private fun CategorySelectionDialog(
    onCategorySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val categories = DataSource.getProductCategories() // Use existing DataSource

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select a Category") },
        text = {
            Column {
                categories.forEach { category ->
                    Text(
                        text = category,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onCategorySelected(category)
                                onDismiss()
                            }
                            .padding(vertical = 12.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun ProductCategoriesRow(
    categories: List<String>,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit
) {
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
                colors = CardDefaults.cardColors(
                    containerColor = if (category == selectedCategory) {
                        MaterialTheme.colorScheme.primary // Or any color that indicates selection
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = if (category == selectedCategory) {
                        MaterialTheme.colorScheme.onPrimary // Or a contrasting color for the text on selected background
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
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
@OptIn(ExperimentalSharedTransitionApi::class)
private fun ProductGrid(
    products: List<Product>,
    categories: List<String>, // categories is still available if needed by ProductGrid, or can be removed if not
    onProductClick: (Product) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope // Added parameter
    // Removed onCategoryClick from ProductGrid definition
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            HomeBanner()
        }
        // Ensure this items is from androidx.compose.foundation.lazy.grid.LazyGridScope
        items(products, key = { product -> product.id }) { product ->
            ProductCard(
                productName = product.name,
                productImage = product.image,
                productPrice = product.price,
                productId = product.id, // Pass product ID
                animatedVisibilityScope = animatedVisibilityScope, // Pass scope
                onClick = { onProductClick(product) }
            )
        }
    }
}