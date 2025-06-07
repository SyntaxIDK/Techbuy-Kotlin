package com.example.techbuy.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DataSourceTest {

    @Test
    fun getProductCategories_returnsCorrectCategories() {
        val expectedCategories = listOf("iPhones", "MacBooks")
        val actualCategories = DataSource.getProductCategories()
        assertEquals(expectedCategories.size, actualCategories.size)
        assertEquals(expectedCategories, actualCategories)
    }

    @Test
    fun getProducts_returnsNonEmptyList() {
        val products = DataSource.getProducts()
        assertTrue("Product list should not be empty", products.isNotEmpty())
    }
}
