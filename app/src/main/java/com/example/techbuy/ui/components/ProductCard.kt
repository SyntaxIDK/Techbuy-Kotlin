package com.example.techbuy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme // Added for typography
import androidx.compose.material3.Text
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope // Required for sharedElement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment // Added for alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
// Removed com.example.techbuy.R as it's not directly used in this snippet, assuming productImage is a drawable resource ID


@OptIn(ExperimentalSharedTransitionApi::class) // For sharedElement
@Composable
fun ProductCard(
    productName: String,
    productImage: Int,
    productPrice: Double,
    productId: Int, // Added productId for shared element key
    animatedVisibilityScope: AnimatedVisibilityScope, // Added for shared element
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (pressed) 1.05f else 1f, label = "scale")
    val elevation by animateDpAsState(targetValue = if (pressed) 12.dp else 4.dp, label = "elevation")

    Card(
        modifier = Modifier
            .padding(8.dp)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        try {
                            pressed = true
                            awaitRelease()
                        } finally {
                            pressed = false
                        }
                    },
                    onTap = { onClick() }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), // Increased padding for better spacing
            horizontalAlignment = Alignment.CenterHorizontally // Center content
        ) {
            // Apply sharedElement modifier to the Image
            Image(
                painter = painterResource(id = productImage),
                contentDescription = productName, // Use productName for better accessibility
                modifier = Modifier
                    .height(120.dp) // Give image a fixed height
                    .sharedElement(
                        state = rememberSharedContentState(key = "image-${productId}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
            Spacer(modifier = Modifier.height(8.dp)) // Spacer for visual separation
            Text(
                text = productName,
                style = MaterialTheme.typography.titleMedium // Use a slightly larger style for name
            )
            Spacer(modifier = Modifier.height(4.dp)) // Spacer
            Text(
                text = "$${String.format("%.2f", productPrice)}", // Display formatted price
                style = MaterialTheme.typography.bodyLarge // Use a clear style for price
            )
        }
    }
}