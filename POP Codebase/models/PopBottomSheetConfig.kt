package com.pop.components.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Shape types for gradient background in PopBottomSheet
 */
enum class BottomSheetGradientShape {
    /** Uses RoundedCornerShape - standard rounded corners */
    Rounded,
    
    /** Uses SquircleShape - smooth rounded corners (Figma-style) */
    Squircle,
}

/**
 * Background type for PopBottomSheet component.
 * 
 * This sealed class provides type-safe configuration for bottom sheet backgrounds,
 * supporting both gradient (with shape variants) and transparent backgrounds.
 */
@Immutable
sealed class BottomSheetBackgroundType {
    /**
     * Gradient background with configurable shape
     * @param shape The shape type (Rounded, Squircle)
     * Note: Corner radius is taken from PopBottomSheetConfig.cornerRadius
     */
    data class Gradient(
        val shape: BottomSheetGradientShape = BottomSheetGradientShape.Rounded
    ) : BottomSheetBackgroundType()
    
    /** Transparent background (no gradient, no shape) */
    data object Transparent : BottomSheetBackgroundType()
}

/**
 * Configuration for PopBottomSheet animations
 */
@Immutable
data class BottomSheetAnimationConfig(
    /** Animation duration in milliseconds (default: 250ms) */
    val durationMs: Int = 150,
    
    /** Enable slide animation (default: true) */
    val enableSlide: Boolean = true,
    
    /** Enable fade animation (default: true) */
    val enableFade: Boolean = true,
    
    /** Enable scale animation (default: true) */
    val enableScale: Boolean = true,
    
    /** Callback when enter animation completes */
    val onEnterAnimationComplete: (() -> Unit)? = null,
    
    /** Callback when exit animation starts */
    val onExitAnimationStart: (() -> Unit)? = null,
    
    /** Callback when exit animation completes */
    val onExitAnimationComplete: (() -> Unit)? = null
)

/**
 * Configuration for PopBottomSheet scrim/overlay
 */
@Immutable
data class BottomSheetScrimConfig(
    /** Enable scrim overlay (default: true) */
    val enabled: Boolean = true,
    
    /** Scrim color (default: dark with 0-70% gradient) */
    val color: Color = Color(0xFF0D0D0D),
    
    /** Scrim start alpha (default: 0f - transparent) */
    val startAlpha: Float = 0f,
    
    /** Scrim end alpha (default: 0.7f - 70% opaque) */
    val endAlpha: Float = 0.7f,
    
    /** Enable blur effect on scrim (API 31+, default: true) */
    val enableBlur: Boolean = true,
    
    /** Blur radius (default: 22f) */
    val blurRadius: Float = 22f
)

/**
 * Configuration data class for PopBottomSheet component.
 * 
 * This provides a structured way to configure all aspects of the bottom sheet,
 * following the design system's configuration pattern.
 * 
 * **Example usage:**
 * ```
 * val config = PopBottomSheetConfig(
 *     cornerRadius = 24.dp,
 *     isDraggable = false,
 *     showFloatingCloseButton = true,
 *     backgroundType = BottomSheetBackgroundType.Gradient(
 *         shape = BottomSheetGradientShape.RoundedCorner
 *     ),
 *     cornerRadius = 24.dp,
 *     animationConfig = BottomSheetAnimationConfig(
 *         durationMs = 300,
 *         onEnterAnimationComplete = { /* handle */ }
 *     )
 * )
 * 
 * PopBottomSheet(
 *     showSheet = showSheet,
 *     onDismissRequest = { showSheet = false },
 *     config = config
 * ) {
 *     // Content
 * }
 * ```
 */
@Immutable
data class PopBottomSheetConfig(
    /** Corner radius for all corners (default: 16dp) */
    val cornerRadius: Dp = 16.dp,
    
    /** Whether the bottom sheet can be dragged to dismiss (default: true) */
    val isDraggable: Boolean = true,
    
    /** Whether the bottom sheet can be dismissed by tapping outside or pressing back (default: true) */
    val isCancellable: Boolean = true,
    
    /** Whether to show a floating close button above the bottom sheet (default: false) */
    val showFloatingCloseButton: Boolean = false,
    
    /** Background type - gradient with shape support or transparent (default: Transparent) */
    val backgroundType: BottomSheetBackgroundType = BottomSheetBackgroundType.Transparent,
    
    /** Animation configuration */
    val animationConfig: BottomSheetAnimationConfig = BottomSheetAnimationConfig(),
    
    /** Scrim/overlay configuration */
    val scrimConfig: BottomSheetScrimConfig = BottomSheetScrimConfig(),

    /** Top Visual Element */
    val topVisualElement: VisualElement? = null,
) {
    companion object {
        /**
         * Creates a default PopBottomSheetConfig for preview purposes
         */
        fun default(
            cornerRadius: Dp = 16.dp,
            showFloatingCloseButton: Boolean = false
        ) = PopBottomSheetConfig(
            cornerRadius = cornerRadius,
            showFloatingCloseButton = showFloatingCloseButton
        )
    }
}
