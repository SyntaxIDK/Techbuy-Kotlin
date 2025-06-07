package com.example.techbuy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable // Added import
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.techbuy.R

@Composable
fun ProductCard(productName: String, productImage: Int, onClick: () -> Unit) { // Added onClick parameter
    Card(modifier = Modifier.padding(8.dp).clickable { onClick() }) { // Added clickable modifier
        Column(modifier = Modifier.padding(8.dp)) {
            Image(painter = painterResource(id = productImage), contentDescription = null)
            Text(text = productName)
        }
    }
}