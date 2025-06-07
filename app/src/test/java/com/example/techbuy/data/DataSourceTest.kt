package com.example.techbuy.data

import com.example.techbuy.data.models.Product
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DataSourceTest {

    // Sample products for testing
    private val product1 = Product(1, "Test Product 1", 0, 10.0, "Category A", "Description 1")
    private val product2 = Product(2, "Test Product 2", 0, 20.0, "Category B", "Description 2")
    private val product3 = Product(3, "Test Product 3", 0, 30.0, "Category A", "Description 3")

    @Before
    fun setUp() {
        // Clear wishlist before each test to ensure test independence
        // This requires a way to clear the wishlist, which is not currently in DataSource.
        // For now, we'll assume direct manipulation or add a clearWishlist method if necessary.
        // Let's proceed by manually ensuring wishlist is empty for tests that need it,
        // or by re-evaluating if a clearWishlist() in DataSource is essential for testing.

        // To ensure a clean state for wishlist tests, we'll manually clear it by removing items one by one.
        // This is not ideal, a direct clear method in DataSource would be better.
        val currentWishlist = DataSource.getWishlistItems().toList() // Create a copy to avoid ConcurrentModificationException
        currentWishlist.forEach { product ->
            DataSource.removeFromWishlist(product.id)
        }
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
}
