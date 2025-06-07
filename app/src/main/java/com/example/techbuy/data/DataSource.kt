package com.example.techbuy.data

import com.example.techbuy.data.models.Product
import com.example.techbuy.data.models.CartItem
import com.example.techbuy.data.models.User // Added import
import com.example.techbuy.R

object DataSource {
    private val cartItems = mutableListOf<CartItem>()
    private val wishlistItems = mutableListOf<Product>()

    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "iPhone 16",
                image = R.drawable.iphone16,
                price = 999.99,
                category = "Smartphones",
                description = "The latest iPhone with an advanced dual-camera system, A17 Bionic chip, and even longer battery life."
            ),
            Product(
                id = 2,
                name = "iPhone 16 Pro",
                image = R.drawable.iphone16p,
                price = 1199.99,
                category = "Smartphones",
                description = "The ultimate iPhone experience with a Pro camera system, ProMotion technology, and the powerful A17 Bionic chip."
            ),
            Product(
                id = 3,
                name = "iPhone 16 Pro Max",
                image = R.drawable.iphone16pm,
                price = 1299.99,
                category = "Smartphones",
                description = "The largest and most advanced iPhone, featuring a stunning display, incredible camera capabilities, and maximum performance."
            ),
            Product(
                id = 4,
                name = "MacBook M1",
                image = R.drawable.macbookm1,
                price = 1999.99,
                category = "Laptops",
                description = "The groundbreaking MacBook with the Apple M1 chip, offering incredible performance and battery life in a thin and light design."
            ),
            Product(
                id = 5,
                name = "MacBook M2",
                image = R.drawable.macbookm2,
                price = 2599.99,
                category = "Laptops",
                description = "Experience next-level performance with the MacBook powered by the Apple M2 chip, perfect for demanding workflows."
            ),
            Product(
                id = 6,
                name = "MacBook M3",
                image = R.drawable.macbookm3,
                price = 2999.99,
                category = "Laptops",
                description = "Unleash your creativity with the MacBook featuring the cutting-edge Apple M3 chip, delivering power and efficiency."
            ),
            Product(
                id = 7,
                name = "MacBook M4",
                image = R.drawable.macbookm4,
                price = 3599.99,
                category = "Laptops",
                description = "The pinnacle of MacBook performance, the M4 model redefines what's possible in a portable Mac."
            )
        )
    }

    fun getProductCategories(): List<String> {
        return listOf("Smartphones", "Laptops") // Updated categories to be more generic
    }

    fun getProductById(productId: Int): Product? {
        return getProducts().find { it.id == productId }
    }

    fun addToCart(product: Product, quantity: Int) {
        val existingItemIndex = cartItems.indexOfFirst { it.product.id == product.id }
        if (existingItemIndex != -1) {
            val existingItem = cartItems[existingItemIndex]
            // Create a new CartItem instance
            cartItems[existingItemIndex] = CartItem(existingItem.product, existingItem.quantity + quantity)
        } else {
            cartItems.add(CartItem(product, quantity))
        }
    }

    fun getCartItems(): List<CartItem> {
        return cartItems.toList() // Return a copy to prevent external modification
    }

    fun getCartItemCount(): Int {
        return cartItems.sumOf { it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun removeCartItem(productId: Int) {
        cartItems.removeAll { it.product.id == productId }
    }

    fun decreaseCartItemQuantity(productId: Int) {
        val existingItemIndex = cartItems.indexOfFirst { it.product.id == productId }
        if (existingItemIndex != -1) {
            val existingItem = cartItems[existingItemIndex]
            if (existingItem.quantity > 1) {
                // Create a new CartItem instance
                cartItems[existingItemIndex] = CartItem(existingItem.product, existingItem.quantity - 1)
            } else {
                removeCartItem(productId) // This already removes the item
            }
        }
    }

    fun getSampleUser(): User {
        return User(
            id = 1,
            name = "Jane Doe",
            email = "jane.doe@example.com",
            password = "samplePassword123" // Password field exists, so providing a sample
        )
    }

    fun addToWishlist(product: Product) {
        if (!wishlistItems.contains(product)) {
            wishlistItems.add(product)
        }
    }

    fun removeFromWishlist(productId: Int) {
        wishlistItems.removeAll { it.id == productId }
    }

    fun getWishlistItems(): List<Product> {
        return wishlistItems.toList()
    }

    fun isProductInWishlist(productId: Int): Boolean {
        return wishlistItems.any { it.id == productId }
    }
}
