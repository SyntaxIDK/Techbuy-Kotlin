package com.example.techbuy.data

import com.example.techbuy.data.models.Product
import com.example.techbuy.R

object DataSource {
    fun getProducts(): List<Product> {
        return listOf(
            Product(1, "iPhone 13", 999.99, R.drawable.iphone),
            Product(2, "MacBook Pro", 1999.99, R.drawable.macbook)
        )
    }
}