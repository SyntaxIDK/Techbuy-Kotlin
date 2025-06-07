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
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.techbuy.data.DataSource
import com.example.techbuy.data.models.Product
import kotlinx.coroutines.delay

@Composable
fun ShimmerPlaceholder(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f, // This value should be adjusted based on the width of the composable
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, delayMillis = 300),
        ), label = "shimmerTranslateAnim"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.9f)
        ),
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Box(modifier = modifier.background(brush))
}

// Extracted composable for Product Image
@Composable
fun ProductImage(product: Product) {
    var showShimmer by remember { mutableStateOf(true) }
    val imageModifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)

    LaunchedEffect(Unit) {
        delay(1000) // Simulate loading delay for 1 second
        showShimmer = false
    }

    Box(modifier = imageModifier) {
        if (showShimmer) {
            ShimmerPlaceholder(modifier = Modifier.fillMaxSize())
        }
        Image(
            painter = painterResource(id = product.image),
            contentDescription = product.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            alpha = if (showShimmer) 0f else 1f // Hide image content while shimmer is visible
        )
    }
}

// Extracted composable for Product Information
@Composable
fun ProductInformation(product: Product) {
    Text(
        text = product.name,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Text(
        text = "Price: \$${String.format("%.2f", product.price)}",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Text(
        text = product.description,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(), // Increased vertical padding
        textAlign = TextAlign.Start
    )
}

// Extracted composable for Quantity Selector
@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityDecrease: () -> Unit,
    onQuantityIncrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        IconButton(onClick = onQuantityDecrease) {
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
        IconButton(onClick = onQuantityIncrease) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Increase quantity"
            )
        }
    }
}

// Extracted composable for Action Buttons
@Composable
fun ProductActionButtons(
    product: Product,
    isInWishlist: Boolean,
    onAddToCartClick: () -> Unit,
    onToggleWishlistClick: () -> Unit
) {
    Button( // "Add to Cart" Button
        onClick = onAddToCartClick,
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Add to Cart"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add to Cart")
        }
    }

    // "Add/Remove Wishlist" Button
    if (isInWishlist) {
        OutlinedButton(
            onClick = onToggleWishlistClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite, // Icon when in wishlist
                contentDescription = "Remove from Wishlist",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Remove from Wishlist")
        }
    } else {
        FilledTonalButton(
            onClick = onToggleWishlistClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.filledTonalButtonColors( // Optional: more explicit control
                // containerColor = MaterialTheme.colorScheme.secondaryContainer,
                // contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder, // Icon when not in wishlist
                contentDescription = "Add to Wishlist",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Add to Wishlist")
        }
    }
}

@Composable
fun OptionChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.padding(end = 8.dp) // Add some spacing between chips
    ) {
        Text(text)
    }
}

@Composable
fun ColorSelector(
    colors: List<String>,
    selectedColor: String?,
    onColorSelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("Color:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            colors.forEach { color ->
                OptionChip(
                    text = color,
                    isSelected = selectedColor == color,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
fun RomSelector(
    romOptions: List<String>,
    selectedRom: String?,
    onRomSelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("Storage:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            romOptions.forEach { rom ->
                OptionChip(
                    text = rom,
                    isSelected = selectedRom == rom,
                    onClick = { onRomSelected(rom) }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavHostController, productId: Int) {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var quantity by remember { mutableIntStateOf(1) } // Corrected mutableIntStateOf
    var isInWishlist by rememberSaveable { mutableStateOf(false) }
    val selectedColor = rememberSaveable { mutableStateOf<String?>(null) }
    val selectedRom = rememberSaveable { mutableStateOf<String?>(null) }


    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        isLoading = true
        error = null // Reset error on new product load
        selectedColor.value = null // Reset color selection
        selectedRom.value = null // Reset ROM selection
        try {
            val fetchedProduct = DataSource.getProductById(productId)
            product = fetchedProduct
            if (fetchedProduct == null) {
                error = "Product not found."
            } else {
                isInWishlist = DataSource.isProductInWishlist(productId)
                // Initialize selections
                if (fetchedProduct.colors.isNotEmpty()) {
                    selectedColor.value = fetchedProduct.colors.first()
                }
                if (fetchedProduct.romOptions.isNotEmpty()) {
                    selectedRom.value = fetchedProduct.romOptions.first()
                }
            }
        } catch (e: Exception) {
            error = "Error loading product details."
        }
        isLoading = false
    }

    // LaunchedEffect to update isInWishlist if the product ID changes or the global wishlist changes.
    LaunchedEffect(product?.id, DataSource.getWishlistItems()) {
        product?.let { prod ->
            if (prod.id == productId) { // Ensure we are checking for the current product
                isInWishlist = DataSource.isProductInWishlist(prod.id)
            }
        }
    }

    // Initialize selectedColor and selectedRom when product data is available
    LaunchedEffect(product) {
        product?.let {
            if (it.colors.isNotEmpty() && selectedColor.value == null) {
                selectedColor.value = it.colors.first()
            }
            if (it.romOptions.isNotEmpty() && selectedRom.value == null) {
                selectedRom.value = it.romOptions.first()
            }
        }
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
                    // Wishlist Icon Button
                    IconButton(onClick = {
                        navController.navigate("wishlist")
                    }) {
                        Icon(
                            imageVector = if (isInWishlist) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isInWishlist) "Remove from Wishlist" else "Add to Wishlist",
                            tint = if (isInWishlist) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    // Cart Icon Button
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
                        ProductImage(product = currentProduct)
                    }
                    item { // Spacer after Image
                        Spacer(modifier = Modifier.height(16.dp))
                        ProductInformation(product = currentProduct)
                    }

                    // Color Selector
                    if (currentProduct.colors.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            ColorSelector(
                                colors = currentProduct.colors,
                                selectedColor = selectedColor.value,
                                onColorSelected = { color -> selectedColor.value = color }
                            )
                        }
                    }

                    // ROM Selector
                    if (currentProduct.romOptions.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            RomSelector(
                                romOptions = currentProduct.romOptions,
                                selectedRom = selectedRom.value,
                                onRomSelected = { rom -> selectedRom.value = rom }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        QuantitySelector(
                            quantity = quantity,
                            onQuantityDecrease = { if (quantity > 1) quantity-- },
                            onQuantityIncrease = { quantity++ }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        ProductActionButtons(
                            product = currentProduct,
                            isInWishlist = isInWishlist,
                            onAddToCartClick = {
                                DataSource.addToCart(currentProduct, quantity, selectedColor.value, selectedRom.value)
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "${currentProduct.name} added to cart",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                            onToggleWishlistClick = {
                                if (isInWishlist) {
                                    DataSource.removeFromWishlist(currentProduct.id)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "${currentProduct.name} removed from wishlist",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                } else {
                                    DataSource.addToWishlist(currentProduct)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "${currentProduct.name} added to wishlist",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                                isInWishlist = !isInWishlist // Toggle state locally
                            }
                        )
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