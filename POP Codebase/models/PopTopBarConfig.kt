package com.pop.components.models

import androidx.compose.runtime.Immutable

/**
 * Merchant image size options for the top bar
 */
enum class MerchantImageSize {
    /** Extra Large merchant image - 320dp total height */
    XLarge,
    /** Large merchant image - 320dp total height */
    Large,
    /** Medium merchant image - 260dp total height */
    Medium,
    /** Small merchant image - variable height */
    Small
}

/**
 * Status bar theme options
 */
enum class StatusBarTheme {
    /** Light status bar (dark icons) */
    Light,
    /** Dark status bar (light icons) */
    Dark,
    /** Transparent status bar */
    Transparent
}

/**
 * Scroll state for the top bar
 */
enum class TopBarScrollState {
    /** Fully expanded state */
    Expanded,
    /** Fully collapsed state */
    Collapsed,
    /** Intermediate scrolling state */
    Intermediate
}

/**
 * Configuration data class for PopTopBar component
 *
 * @property appBarConfig Configuration for the app bar
 * @property titleBarConfig Configuration for the title bar at the bottom
 * @property merchantImage Optional merchant visual element (Image, Lottie, ColorComponent) to display as background
 * @property merchantImageSize Size of the merchant image (Large, Medium, Small)
 * @property useAspectRatioBasedHeight If true, calculate height based on screen width and aspect ratio. If false, use fixed heights (legacy behavior). Default: true
 * @property statusBarTheme Theme for the status bar (Light, Dark, Transparent)
 * @property scrollState Current scroll state of the top bar
 * @property onScrollStateChange Callback when scroll state changes
 * @property showDivider Whether to show the divider at the bottom of the top bar (default: true)
 * @property onMerchantImageClick Optional callback when merchant image is clicked
 */
@Immutable
data class PopTopBarConfig(
    val titleBarConfig: PopTitleBarConfig? = null ,
    val merchantImage: VisualElement? = null,
    val merchantImageSize: MerchantImageSize = MerchantImageSize.Large,
    val useAspectRatioBasedHeight: Boolean = true, // Default to new behavior
    val statusBarTheme: StatusBarTheme = StatusBarTheme.Transparent,
    val scrollState: TopBarScrollState = TopBarScrollState.Expanded,
    val onScrollStateChange: ((TopBarScrollState) -> Unit)? = null,
    val showDivider: Boolean = true,
    val onMerchantImageClick: (() -> Unit)? = null
) {
    companion object {
        /**
         * Creates a default PopTopBarConfig for preview purposes
         */
        fun default(
            titleText: String = "",
            bodyText: String? = "",
        ) = PopTopBarConfig(
            titleBarConfig = PopTitleBarConfig.default(
                titleText = titleText,
                bodyText = bodyText
            )
        )
    }
}
