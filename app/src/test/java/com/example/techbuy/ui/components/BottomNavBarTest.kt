package com.example.techbuy.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavHostController
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE) // Add this if you encounter issues with AndroidManifest.xml
class BottomNavBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNavBar_displaysShopButton() {
        // Mock NavController
        val mockNavController = mockk<NavHostController>(relaxed = true)

        // Set content with BottomNavBar
        composeTestRule.setContent {
            BottomNavBar(navController = mockNavController)
        }

        // Check for the Shop button's icon by content description
        // The content description for the Shop button was set to "Shop"
        composeTestRule.onNodeWithContentDescription("Shop")
            .assertIsDisplayed()

        // Check for the Home button
        composeTestRule.onNodeWithContentDescription("Home")
            .assertIsDisplayed()

        // Check for the Shopping Cart button
        composeTestRule.onNodeWithContentDescription("Shopping Cart")
            .assertIsDisplayed()

        // Check for the Profile button
        composeTestRule.onNodeWithContentDescription("Profile")
            .assertIsDisplayed()
    }
}
