package com.pop.components.ui.config

/**
 * Configuration data class for end CTA (Call-to-Action) in toast messages.
 *
 * @param text The text to display on the CTA button
 * @param action The action identifier for the CTA
 * @param onClick The callback function to execute when the CTA is clicked
 */
data class EndCtaConfig(
    val text: String,
    val action: String,
    val onClick: () -> Unit
)

