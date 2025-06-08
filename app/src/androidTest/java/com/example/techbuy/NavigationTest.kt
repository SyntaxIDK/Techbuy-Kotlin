package com.example.techbuy

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techbuy.ui.screens.ProductsScreen // May be needed for context, though not directly used for node finding
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigateToProductsScreen_viaShopButton() {
        // Wait for the UI to settle, especially if there's a splash screen or initial loading.
        // composeTestRule.waitUntil(timeoutMillis = 5000) {
        //    try {
        //        composeTestRule.onNodeWithContentDescription("Shop").assertExists()
        //        true
        //    } catch (e: AssertionError) {
        //        false
        //    }
        // }
        // It's possible that the BottomNavBar is not immediately available if it's part of HomeScreen,
        // which might require navigating from WelcomeScreen first.
        // For now, assume WelcomeScreen automatically navigates to HomeScreen or that BottomNavBar is on WelcomeScreen.

        // Step 1: Click "Get Started" on WelcomeScreen to navigate to HomeScreen
        // Assuming "Get Started" is the text on the button in WelcomeScreen
        composeTestRule.onNodeWithText("Get Started").performClick()

        // Wait for HomeScreen to load and the "Shop" button to be available.
        // The shopButton might take a moment to appear after navigation.
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithContentDescription("Shop").assertExists()
                true // Element found
            } catch (e: AssertionError) {
                false // Element not found yet
            }
        }

        // Step 2: Find the "Shop" button in the NavigationBar within HomeScreen
        // The content description for the Icon in NavigationBarItem is item.first, which is "Shop"
        val shopButton = composeTestRule.onNodeWithContentDescription("Shop")

        // Assert it's displayed and perform a click
        shopButton.assertIsDisplayed()
        shopButton.performClick()

        // Verify that the ProductsScreen is displayed
        // We can check for a UI element unique to ProductsScreen, e.g., the title "Products List"
        composeTestRule.onNodeWithText("Products List").assertIsDisplayed()
    }
}
