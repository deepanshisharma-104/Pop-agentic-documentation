package com.pop.components.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.pop.components.ds_components.PopListItemAvatar
import com.pop.components.theme.IconName
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor

/**
 * Configuration data class for PopTitleBar component
 *
 * @property titleText Title text content
 * @property bodyText Body text content (nullable)
 * @property titleLeftIcon Left icon for title (nullable)
 * @property titleRightIcon Right icon for title (nullable)
 * @property bodyLeftIcon Left icon for body (nullable)
 * @property bodyRightIcon Right icon for body (nullable)
 * @property titleTextStyle Custom text style for title (nullable, defaults to h2)
 * @property bodyTextStyle Custom text style for body (nullable, defaults to label2)
 * @property titleTextColor Title text color (default: TextColor.Primary)
 * @property bodyTextColor Body text color (default: TextColor.Tertiary)
 * @property enableMarquee Enable marquee effect when text overflows (default: true)
 * @property marqueeSpeedPxPerSecond Scroll speed for marquee text (default: 100f)
 * @property marqueeStayDurationMs Pause duration when marquee finishes one loop (default: 1500ms)
 * @property marqueeLoopCount Number of marquee loops to run (null for infinite)
 * @property gradientLeft Enable left gradient when marquee is active (default: false)
 * @property gradientRight Enable right gradient when marquee is active (default: false)
 * @property gradientWidth Width of gradient - can be Small, Big, or Auto (default: Auto)
 * @property backgroundColor Background color for gradient blending (default: SurfaceColor.Secondary)
 * @property iconSizeDp Size of icons in dp (default: 16 for title, 12 for body)
 * @property iconGapDp Gap between icon and text in dp (default: 6)
 * @property titleBodyGapDp Gap between title and body in dp (default: 6)
 * @property paddingDp Padding around the component in dp (default: 12)
 * @property bodyMaxLines Maximum number of lines for body text (default: 2)
 * @property titleMaxLines Maximum number of lines for title text (default: 1)
 */
@Immutable
data class PopTitleBarConfig(
    val titleText: String,
    val bodyText: String? = null,
    val titleLeftIcon: IconName? = null,
    val titleRightIcon: IconName? = null,
    val bodyLeftIcon: IconName? = null,
    val bodyRightIcon: IconName? = null,
    val titleTextStyle: TextStyle? = null,
    val bodyTextStyle: TextStyle? = null,
    val titleTextColor: Color = TextColor.Primary,
    val bodyTextColor: Color = TextColor.Tertiary,
    val enableMarquee: Boolean = true,
    val marqueeSpeedPxPerSecond: Float = 100f,
    val marqueeStayDurationMs: Long = 1500L,
    val marqueeLoopCount: Int? = null,
    val gradientLeft: Boolean = false,
    val gradientRight: Boolean = false,
    val gradientWidth: GradientWidth? = null, // null means auto-select based on text width
    val backgroundColor: Color = SurfaceColor.Secondary,
    val titleIconSizeDp: Float = 16f,
    val bodyIconSizeDp: Float = 12f,
    val iconGapDp: Int = 6,
    val titleBodyGapDp: Int = 6,
    val paddingDp: Int = 16,
    val bodyMaxLines: Int = 2,
    val titleMaxLines: Int = 1
) {
    companion object {
        /**
         * Creates a default PopTitleBarConfig for preview purposes
         */
        fun default(titleText: String = "Title", bodyText: String? = "Body") =
            PopTitleBarConfig(
                titleText = titleText,
                bodyText = bodyText
            )
    }
}

