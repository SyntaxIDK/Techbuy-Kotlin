package com.example.techbuy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart // Added import
import androidx.compose.material3.*
import androidx.compose.material3.Badge // Added import
import androidx.compose.material3.BadgedBox // Added import
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.techbuy.data.DataSource
import com.example.techbuy.data.models.Product // Keep this import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavHostController, productId: Int) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var quantity by remember { mutableIntStateOf(1) } // Corrected mutableIntStateOf
    var isInWishlist by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        isLoading = true
        try {
            val fetchedProduct = DataSource.getProductById(productId)
            product = fetchedProduct
            if (fetchedProduct == null) {
                error = "Product not found."
            } else {
                isInWishlist = DataSource.isProductInWishlist(productId)
            }
        } catch (e: Exception) {
            error = "Error loading product details."
        }
        isLoading = false
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        BadgedBox(
                            badge = {
                                val cartItemCount = DataSource.getCartItemCount() // Get current count
                                if (cartItemCount > 0) {
                                    Badge { Text(cartItemCount.toString()) }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Shopping Cart"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null) {
                Text(
                    text = error!!,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (product != null) {
                val currentProduct = product!!
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Image(
                            painter = painterResource(id = currentProduct.image),
                            contentDescription = currentProduct.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentScale = ContentScale.Fit
                        )
                    }
                    item {
                        Text(
                            text = currentProduct.name,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    item {
                        Text(
                            text = "Price: \$${String.format("%.2f", currentProduct.price)}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    item {
                        Text(
                            text = currentProduct.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                    item {
                        Text(
                            "Specifications Placeholder",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Icon(
                                    imageVector = Icons.Filled.Remove,
                                    contentDescription = "Decrease quantity"
                                )
                            }
                            Text(
                                text = quantity.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            IconButton(onClick = { quantity++ }) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Increase quantity"
                                )
                            }
                        }
                    }
                    item {
                        Button(
                            onClick = {
                                currentProduct?.let { prod ->
                                    DataSource.addToCart(prod, quantity)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "${prod.name} added to cart",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text("Add to Cart")
                        }
                    }
                    item {
                        Button(
                            onClick = {
                                currentProduct?.let { prod ->
                                    if (isInWishlist) {
                                        DataSource.removeFromWishlist(prod.id)
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "${prod.name} removed from wishlist",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    } else {
                                        DataSource.addToWishlist(prod)
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "${prod.name} added to wishlist",
                                                duration = SnackbarDuration.Short
                                            )
                                        }
                                    }
                                    isInWishlist = !isInWishlist
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp) // Add some space between buttons
                        ) {
                            Icon(
                                imageVector = if (isInWishlist) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (isInWishlist) "Remove from Wishlist" else "Add to Wishlist",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(if (isInWishlist) "Remove from Wishlist" else "Add to Wishlist")
                        }
                    }
                }
            } else {
                // This case should ideally be covered by error state if product is null after loading
                Text(
                    text = "Product not available.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}