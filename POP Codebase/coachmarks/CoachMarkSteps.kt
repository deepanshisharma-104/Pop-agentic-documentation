package com.pop.components.coachmarks

import androidx.compose.ui.geometry.Rect

/**
 * Central repository for all coach mark step configurations.
 *
 * Add your coach mark flows here to keep them organized in one place.
 *
 * Usage:
 * ```kotlin
 * // In your activity or fragment:
 * coachMarkHelper.show(
 *     steps = CoachMarkSteps.homeOnboarding()
 * )
 * ```
 */
object CoachMarkSteps {

    /**
     * Home screen onboarding coach marks.
     * Highlights key features like QR scanner and bottom navigation.
     */
    fun homeOnboarding(rcbpEnabled: Boolean = true): List<CoachMarkStep> {
        return listOfNotNull(
            // Step 1: QR Scanner Button
            CoachMarkStep(
                id = "qr_scanner",
                title = "Scan any QR in a heartbeat",
                subtitle = null,
                targetViewKey = "qr_scanner_button",
                highlightStyle = HighlightStyle.BORDER,
                dialogPosition = DialogPosition.AUTO,
                dialogAlignment = DialogAlignment.AUTO
            ),
            // Step 2: Bottom Navigation Bar
            CoachMarkStep(
                id = "bottom_nav",
                title = "Navigate to Shop, Cards and back to Home",
                subtitle = null,
                targetViewKey = "bottom_nav_bar",
                highlightStyle = HighlightStyle.BORDER,
                dialogPosition = DialogPosition.TOP,
                dialogAlignment = DialogAlignment.CENTER
            ),
            // Step 3: Profile Icon (home page top-left)
            CoachMarkStep(
                id = "profile_icon",
                title = "Your profile just moved to this corner",
                subtitle = "That sweet Cashback Zone is also here",
                targetViewKey = "home_profile_icon",
                highlightStyle = HighlightStyle.CIRCLE,
                dialogPosition = DialogPosition.BOTTOM,
                dialogAlignment = DialogAlignment.START
            ),
            // Step 4: POPcoins Badge (home page top-right)
            CoachMarkStep(
                id = "popcoin_badge",
                title = "Flex your POPcoins from here",
                subtitle = null,
                targetViewKey = "home_popcoin_badge",
                highlightStyle = HighlightStyle.BORDER,
                dialogPosition = DialogPosition.BOTTOM,
                dialogAlignment = DialogAlignment.END
            ),
            // Step 5: Everything UPI
            CoachMarkStep(
                id = "everything_upi",
                title = "This is your UPI Command Center",
                subtitle = "Pay with UPI, check balance and more...",
                targetViewKey = "home_everything_upi",
                highlightStyle = HighlightStyle.BORDER,
                dialogPosition = DialogPosition.TOP,
                dialogAlignment = DialogAlignment.CENTER
            ),
            // Step 6: Recharge & Bills — only shown when the rcbp feature flag is on
            if (rcbpEnabled) CoachMarkStep(
                id = "recharge_n_bills",
                title = "POP your bills from here",
                subtitle = "Pay them early. Don't let them linger.",
                targetViewKey = "home_recharge_n_bills",
                highlightStyle = HighlightStyle.BORDER,
                dialogPosition = DialogPosition.TOP,
                dialogAlignment = DialogAlignment.CENTER
            ) else null,
        )
    }

    /**
     * Payment features onboarding.
     * Highlights UPI card and payment options.
     */
    fun paymentOnboarding(): List<CoachMarkStep> {
        return listOf(
            CoachMarkStep(
                id = "upi_card",
                title = "This is your UPI Command Center",
                subtitle = "Pay with UPI, check balance and more",
                targetViewKey = "upi_card",
                highlightStyle = HighlightStyle.FILLED_SQUIRCLE,
                dialogPosition = DialogPosition.TOP,
                dialogAlignment = DialogAlignment.CENTER
            )
        )
    }

    /**
     * Example: Custom coach mark with hardcoded coordinates (not recommended).
     * Prefer using targetViewKey with registered components for dynamic positioning.
     */
    fun customCoachMark(targetRect: Rect): List<CoachMarkStep> {
        return listOf(
            CoachMarkStep(
                id = "custom",
                title = "Custom Coach Mark",
                subtitle = "This uses hardcoded coordinates",
                targetRect = targetRect,
                highlightStyle = HighlightStyle.BORDER,
                dialogPosition = DialogPosition.AUTO,
                dialogAlignment = DialogAlignment.AUTO
            )
        )
    }

    // Add more coach mark flows here as needed:
    // - fun shopOnboarding(): List<CoachMarkStep>
    // - fun cardsOnboarding(): List<CoachMarkStep>
    // - fun profileOnboarding(): List<CoachMarkStep>
    // etc.
}
