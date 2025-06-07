package com.example.techbuy.data

import com.example.techbuy.data.models.Product
import com.example.techbuy.R

object DataSource {
    fun getProducts(): List<Product> {
        return listOf(
            Product(1, "iPhone 16", 999.99, R.drawable.iphone16),
            Product(2, "iPhone 16 Pro", 1199.99, R.drawable.iphone16p),
            Product(3, "iPhone 16 Pro Max", 1299.99, R.drawable.iphone16pm),
            Product(4, "MacBook M1", 1999.99, R.drawable.macbookm1),
            Product(5, "MacBook M2", 2599.99, R.drawable.macbookm2),
            Product(6, "MacBook M3", 2999.99, R.drawable.macbookm3),
            Product(7, "MacBook M4", 3599.99, R.drawable.macbookm4),
        )
    }
}