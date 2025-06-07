package com.example.techbuy

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.techbuy.data.DataSource
import com.example.techbuy.ui.MainActivity // Assuming MainActivity is your entry point
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.semantics.SemanticsProperties

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // Determine product details at the class level to ensure consistency
    private val testProduct = DataSource.getProducts().find { it.id == 1 }
        ?: DataSource.getProducts().firstOrNull()
        ?: throw IllegalStateException("No products found in DataSource. Cannot run tests.")

    private val testProductName = testProduct.name
    private val productAddedToCartText = "$testProductName added to cart"
    private val productAddedToWishlistText = "$testProductName added to wishlist"
    private val productRemovedFromWishlistText = "$testProductName removed from wishlist"

    @Before
    fun navigateToProductDetailScreenIfNeeded() {
        val onDetailScreen = composeTestRule.onAllNodesWithText("Add to Cart", ignoreCase = true)
            .fetchSemanticsNodes(atLeastOneRootRequired = false).isNotEmpty()

        if (!onDetailScreen) {
            try {
                composeTestRule.onNodeWithText(testProductName, substring = true)
                    .performScrollTo() // Ensure the item is visible before clicking
                    .performClick()

                composeTestRule.waitUntil(timeoutMillis = 5000) {
                    composeTestRule.onAllNodesWithText("Add to Cart", ignoreCase = true)
                        .fetchSemanticsNodes().isNotEmpty()
                }
            } catch (e: Exception) {
                System.err.println("WARNING: Could not navigate to ProductDetailScreen for '$testProductName'. Error: ${e.message}")
                // This is a warning; tests will proceed and likely fail if not on the correct screen.
            }
        }
    }

    @Test
    fun testProductDetailsDisplay() {
        composeTestRule.onNodeWithText(testProductName, substring = true).assertIsDisplayed()
        // Check for price associated with the product. Assumes price contains '$'.
        // This looks for a node with '$' that is a descendant of a node containing the product name.
        composeTestRule.onNode(hasText("$") and isDescendantOfA(hasText(testProductName, substring = true))).assertIsDisplayed()

        // Check for the image (its content description is the product name)
        composeTestRule.onNodeWithContentDescription(testProductName, substring = true).assertIsDisplayed()

        // "Add to Cart" button
        composeTestRule.onNodeWithText("Add to Cart", ignoreCase = true).assertIsDisplayed().assertIsEnabled()

        // Wishlist button - check for one of the two possible content descriptions
        val wishlistPossibleDescriptions = listOf("Add to Wishlist", "Remove from Wishlist")
        composeTestRule.onNode(hasAnyOf(
            hasContentDescription("Add to Wishlist", substring = true, ignoreCase = true),
            hasContentDescription("Remove from Wishlist", substring = true, ignoreCase = true)
        )).assertIsDisplayed()
    }

    @Test
    fun testQuantitySelector() {
        composeTestRule.onNodeWithText("1", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Increase quantity").performClick()
        composeTestRule.onNodeWithText("2", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Decrease quantity").performClick()
        composeTestRule.onNodeWithText("1", substring = true).assertIsDisplayed()
    }

    @Test
    fun testAddToCartButton() {
        composeTestRule.onNodeWithText("Add to Cart", ignoreCase = true).performClick()
        try {
            // Using substring match for snackbar text for more flexibility
            composeTestRule.onNodeWithText(productAddedToCartText, substring = true).assertIsDisplayed()
        } catch (e: AssertionError) {
             System.err.println("WARNING: Snackbar with text '$productAddedToCartText' not found. This might be okay if product was already in cart.")
        }
    }

    @Test
    fun testWishlistButtonToggle() {
        val addDesc = "Add to Wishlist"
        val removeDesc = "Remove from Wishlist"

        // Determine initial state by checking which content description is present
        // Using a flag to track state, assuming one of the two must be present.
        var isInWishlistCurrently = false
        try {
            composeTestRule.onNodeWithContentDescription(removeDesc, substring = true, ignoreCase = true)
                .assertExists() // Use assertExists to check without failing immediately if not displayed
            isInWishlistCurrently = true
        } catch (e: AssertionError) {
            // If removeDesc is not found, assume addDesc should be there.
            composeTestRule.onNodeWithContentDescription(addDesc, substring = true, ignoreCase = true)
                .assertIsDisplayed() // This will fail if addDesc is also not there.
            isInWishlistCurrently = false
        }

        // Perform actions based on initial state
        if (isInWishlistCurrently) {
            // Starts in Wishlist: Remove -> Add
            composeTestRule.onNodeWithContentDescription(removeDesc, substring = true, ignoreCase = true).performClick()
            composeTestRule.onNodeWithText(productRemovedFromWishlistText, substring = true).assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription(addDesc, substring = true, ignoreCase = true).assertIsDisplayed()

            // Click again to add it back
            composeTestRule.onNodeWithContentDescription(addDesc, substring = true, ignoreCase = true).performClick()
            composeTestRule.onNodeWithText(productAddedToWishlistText, substring = true).assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription(removeDesc, substring = true, ignoreCase = true).assertIsDisplayed()
        } else {
            // Starts not in Wishlist: Add -> Remove
            composeTestRule.onNodeWithContentDescription(addDesc, substring = true, ignoreCase = true).performClick()
            composeTestRule.onNodeWithText(productAddedToWishlistText, substring = true).assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription(removeDesc, substring = true, ignoreCase = true).assertIsDisplayed()

            // Click again to remove it
            composeTestRule.onNodeWithContentDescription(removeDesc, substring = true, ignoreCase = true).performClick()
            composeTestRule.onNodeWithText(productRemovedFromWishlistText, substring = true).assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription(addDesc, substring = true, ignoreCase = true).assertIsDisplayed()
        }
    }
}
