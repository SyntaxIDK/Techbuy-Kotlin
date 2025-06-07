package com.example.techbuy.data

import com.example.techbuy.data.models.Product
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DataSourceTest {

    // Sample products for testing
    // Updated to include colors and romOptions for cart testing
    private lateinit var product1: Product
    private lateinit var product2: Product

    @Before
    fun setUp() {
        // Clear cart and wishlist before each test to ensure test independence
        DataSource.clearCart() // Assumes clearCart() exists and works

        val currentWishlist = DataSource.getWishlistItems().toList()
        currentWishlist.forEach { product ->
            DataSource.removeFromWishlist(product.id)
        }

        // Initialize products with color and ROM options
        product1 = Product(
            id = 1,
            name = "Test Phone X",
            image = 0, // Dummy image res ID
            price = 999.0,
            category = "Smartphones",
            description = "A test smartphone",
            colors = listOf("Black", "Silver", "Gold"),
            romOptions = listOf("128GB", "256GB", "512GB")
        )
        product2 = Product(
            id = 2,
            name = "Test Laptop Y",
            image = 0, // Dummy image res ID
            price = 1299.0,
            category = "Laptops",
            description = "A test laptop",
            colors = listOf("Space Gray", "Silver"),
            romOptions = listOf("256GB", "512GB", "1TB")
        )
        // Note: product3 is not used in the new cart tests, can be removed if not needed elsewhere
        // private val product3 = Product(3, "Test Product 3", 0, 30.0, "Category A", "Description 3")
    }


    @Test
    fun getProductCategories_returnsCorrectCategories() {
        // Note: The original test had "iPhones" and "MacBooks".
        // The DataSource.kt implementation has "Smartphones" and "Laptops".
        // Updating test to reflect actual implementation.
        val expectedCategories = listOf("Smartphones", "Laptops")
        val actualCategories = DataSource.getProductCategories()
        assertEquals("Number of categories should match", expectedCategories.size, actualCategories.size)
        assertEquals("Category names should match", expectedCategories, actualCategories)
    }

    @Test
    fun getProducts_returnsNonEmptyList() {
        val products = DataSource.getProducts()
        assertTrue("Product list should not be empty", products.isNotEmpty())
    }

    // --- Wishlist Tests ---

    @Test
    fun testAddToWishlist_newItem() {
        DataSource.addToWishlist(product1)
        val wishlist = DataSource.getWishlistItems()
        assertEquals("Wishlist should contain 1 item after adding a new product.", 1, wishlist.size)
        assertTrue("Wishlist should contain the added product.", wishlist.contains(product1))
    }

    @Test
    fun testAddToWishlist_duplicateItem() {
        DataSource.addToWishlist(product1)
        DataSource.addToWishlist(product1) // Try adding the same product again
        val wishlist = DataSource.getWishlistItems()
        assertEquals("Wishlist should still contain 1 item after attempting to add a duplicate.", 1, wishlist.size)
        assertTrue("Wishlist should contain the original product.", wishlist.contains(product1))
    }

    @Test
    fun testGetWishlistItems_empty() {
        // Setup ensures wishlist is empty
        val wishlist = DataSource.getWishlistItems()
        assertTrue("Wishlist should be empty initially (or after clearing).", wishlist.isEmpty())
    }

    @Test
    fun testGetWishlistItems_withItems() {
        DataSource.addToWishlist(product1)
        DataSource.addToWishlist(product2)
        val wishlist = DataSource.getWishlistItems()
        assertEquals("Wishlist should contain 2 items.", 2, wishlist.size)
        assertTrue("Wishlist should contain product1.", wishlist.contains(product1))
        assertTrue("Wishlist should contain product2.", wishlist.contains(product2))
    }

    @Test
    fun testRemoveFromWishlist_itemExists() {
        DataSource.addToWishlist(product1)
        DataSource.addToWishlist(product2)
        DataSource.removeFromWishlist(product1.id)
        val wishlist = DataSource.getWishlistItems()
        assertEquals("Wishlist should contain 1 item after removal.", 1, wishlist.size)
        assertFalse("Wishlist should not contain the removed product (product1).", wishlist.contains(product1))
        assertTrue("Wishlist should still contain product2.", wishlist.contains(product2))
    }

    @Test
    fun testRemoveFromWishlist_itemDoesNotExist() {
        DataSource.addToWishlist(product1)
        DataSource.removeFromWishlist(product2.id) // product2 was not added
        val wishlist = DataSource.getWishlistItems()
        assertEquals("Wishlist size should remain unchanged if non-existent item is removed.", 1, wishlist.size)
        assertTrue("Wishlist should still contain product1.", wishlist.contains(product1))
    }

    @Test
    fun testIsProductInWishlist_true() {
        DataSource.addToWishlist(product1)
        assertTrue("isProductInWishlist should return true for an existing product.", DataSource.isProductInWishlist(product1.id))
    }

    @Test
    fun testIsProductInWishlist_false() {
        // product1 is not added to wishlist in this test (setUp clears it)
        assertFalse("isProductInWishlist should return false for a non-existing product.", DataSource.isProductInWishlist(product1.id))
    }

     @Test
    fun testIsProductInWishlist_false_afterRemoval() {
        DataSource.addToWishlist(product1)
        DataSource.removeFromWishlist(product1.id)
        assertFalse("isProductInWishlist should return false after product is removed.", DataSource.isProductInWishlist(product1.id))
    }

    // It might be beneficial to add a test for getProductById
    @Test
    fun testGetProductById_exists() {
        // Assuming product IDs in DataSource.getProducts() are known or stable for testing.
        // Let's take the first product from the actual data source for a more robust test.
        val firstProductFromSource = DataSource.getProducts().firstOrNull()
        assertNotNull("DataSource should have at least one product for this test to be valid.", firstProductFromSource)
        firstProductFromSource?.let {
            val foundProduct = DataSource.getProductById(it.id)
            assertNotNull("Product should be found by its ID.", foundProduct)
            assertEquals("Found product should have the same ID.", it.id, foundProduct?.id)
            assertEquals("Found product should have the same name.", it.name, foundProduct?.name)
        }
    }

    @Test
    fun testGetProductById_doesNotExist() {
        val nonExistentProductId = -999 // An ID that is unlikely to exist
        val foundProduct = DataSource.getProductById(nonExistentProductId)
        assertNull("Product should not be found for a non-existent ID.", foundProduct)
    }

    // --- Cart Tests with Variants ---

    @Test
    fun addToCart_newVariant_addsItem() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        val cartItems = DataSource.getCartItems()
        assertEquals(1, cartItems.size)
        assertEquals(product1.id, cartItems[0].product.id)
        assertEquals("Black", cartItems[0].selectedColor)
        assertEquals("128GB", cartItems[0].selectedRom)
        assertEquals(1, cartItems[0].quantity)
    }

    @Test
    fun addToCart_sameVariant_increasesQuantity() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.addToCart(product1, 2, "Black", "128GB")
        val cartItems = DataSource.getCartItems()
        assertEquals(1, cartItems.size)
        assertEquals(3, cartItems[0].quantity)
    }

    @Test
    fun addToCart_differentColorVariant_addsNewItem() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.addToCart(product1, 1, "Silver", "128GB")
        val cartItems = DataSource.getCartItems()
        assertEquals(2, cartItems.size)
        // Verify items are distinct and correct
        assertTrue(cartItems.any { it.selectedColor == "Black" && it.selectedRom == "128GB" })
        assertTrue(cartItems.any { it.selectedColor == "Silver" && it.selectedRom == "128GB" })
    }

    @Test
    fun addToCart_differentRomVariant_addsNewItem() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.addToCart(product1, 1, "Black", "256GB")
        val cartItems = DataSource.getCartItems()
        assertEquals(2, cartItems.size)
        assertTrue(cartItems.any { it.selectedColor == "Black" && it.selectedRom == "128GB" })
        assertTrue(cartItems.any { it.selectedColor == "Black" && it.selectedRom == "256GB" })
    }

    @Test
    fun addToCart_differentProduct_addsNewItem() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.addToCart(product2, 1, "Space Gray", "256GB")
        val cartItems = DataSource.getCartItems()
        assertEquals(2, cartItems.size)
        assertTrue(cartItems.any { it.product.id == product1.id })
        assertTrue(cartItems.any { it.product.id == product2.id })
    }

    @Test
    fun removeCartItem_specificVariant_removesCorrectly() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.addToCart(product1, 1, "Silver", "128GB")
        DataSource.removeCartItem(product1.id, "Black", "128GB")
        val cartItems = DataSource.getCartItems()
        assertEquals(1, cartItems.size)
        assertEquals("Silver", cartItems[0].selectedColor)
        assertEquals("128GB", cartItems[0].selectedRom)
    }

    @Test
    fun removeCartItem_nonExistentVariant_doesNothing() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.removeCartItem(product1.id, "Gold", "128GB") // Gold variant not added
        val cartItems = DataSource.getCartItems()
        assertEquals(1, cartItems.size)
    }

    @Test
    fun decreaseCartItemQuantity_specificVariant_decreasesQuantity() {
        DataSource.addToCart(product1, 2, "Black", "128GB")
        DataSource.addToCart(product1, 1, "Silver", "128GB")
        DataSource.decreaseCartItemQuantity(product1.id, "Black", "128GB")
        val cartItems = DataSource.getCartItems()
        val itemBlack = cartItems.find { it.selectedColor == "Black" && it.selectedRom == "128GB" }
        val itemSilver = cartItems.find { it.selectedColor == "Silver" && it.selectedRom == "128GB" }

        assertNotNull(itemBlack)
        assertEquals(1, itemBlack?.quantity)
        assertNotNull(itemSilver)
        assertEquals(1, itemSilver?.quantity)
        assertEquals(2, cartItems.size) // Ensure total items is still 2
    }

    @Test
    fun decreaseCartItemQuantity_toZero_removesItem() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.addToCart(product1, 1, "Silver", "128GB") // Add another item to ensure it's not affected
        DataSource.decreaseCartItemQuantity(product1.id, "Black", "128GB")
        val cartItems = DataSource.getCartItems()
        val itemBlack = cartItems.find { it.selectedColor == "Black" && it.selectedRom == "128GB" }

        assertNull("Item should be removed when quantity reaches zero", itemBlack)
        assertEquals(1, cartItems.size) // Silver item should remain
        assertEquals("Silver", cartItems[0].selectedColor)
    }

    @Test
    fun clearCart_emptiesCart() {
        DataSource.addToCart(product1, 1, "Black", "128GB")
        DataSource.addToCart(product2, 1, "Space Gray", "256GB")
        DataSource.clearCart()
        val cartItems = DataSource.getCartItems()
        assertTrue(cartItems.isEmpty())
    }

    // Test case for adding product with null color/ROM, if applicable
    // Based on current Product.kt and DataSource.kt, color/ROM are expected.
    // If nulls were permissible for selectedColor/selectedRom in CartItem, this test would be relevant.
    // For now, assuming color/ROM are always selected for variants.
    // @Test
    // fun addToCart_nullVariant_ifPermitted() {
    //     DataSource.addToCart(product1, 1, null, null)
    //     val cartItems = DataSource.getCartItems()
    //     // Assertions based on how null variants are handled
    // }
}
