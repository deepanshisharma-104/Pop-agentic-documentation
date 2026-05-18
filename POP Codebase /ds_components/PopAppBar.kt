package com.pop.components.ds_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pop.components.models.AppBarIconSlot
import com.pop.components.models.IconAnimationType
import com.pop.components.models.PopAppBarConfig
import com.pop.components.theme.IconName
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopIconResources

// Shared enter/exit animations used by every slot in PopAppBar so all icons
// appear and disappear at the same time with the same feel.
private val appBarSlotFadeSpec: FiniteAnimationSpec<Float> =
    tween(durationMillis = 350, easing = LinearOutSlowInEasing)
private val appBarSlotEnter = fadeIn(appBarSlotFadeSpec)
private val appBarSlotExit = fadeOut(appBarSlotFadeSpec)

@Composable
private fun AppBarSlotVisibility(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = appBarSlotEnter,
        exit = appBarSlotExit
    ) {
        content()
    }
}

/**
 * PopAppBar component - A customizable app bar with navigation icon and actions
 *
 * Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=194-12061&m=dev
 *
 * Features:
 * - Navigation icon (typically back button) on the left
 * - Optional title text
 * - Action icons on the right
 * - Customizable colors and elevation
 * - Optional slot for custom content
 *
 * @param modifier Modifier for the component
 * @param config Configuration for the app bar
 * @param rightSlot Optional composable slot for right side custom content
 * @param leftSlot Optional composable slot for left side custom content (before navigation icon)
 */
@Composable
fun PopAppBar(
    modifier: Modifier = Modifier,
    config: PopAppBarConfig = PopAppBarConfig.default(),
    rightSlot: (@Composable () -> Unit)? = null,
    leftSlot: (@Composable () -> Unit)? = null
) {
    val shouldShowNavigationIcon = config.showNavigationIcon && config.navigationIcon != null

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp) // Add minimum height to prevent shrinking
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left slot and navigation icon are mutually exclusive.
        // Both use AnimatedVisibility so neither snaps in/out abruptly.
        AppBarSlotVisibility(visible = leftSlot != null) {
            leftSlot?.invoke()
        }
        if (leftSlot == null) {
            AppBarSlotVisibility(visible = shouldShowNavigationIcon) {
                NavigationIcon(config = config)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Right: 4 Icon Slots + Composable Slot
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Slot 1
            AnimatedIconSlot(
                slot = config.iconSlots.slot1,
                contentColor = config.contentColor
            )
            // Icon Slot 2
            AnimatedIconSlot(
                slot = config.iconSlots.slot2,
                contentColor = config.contentColor
            )
            // Icon Slot 3
            AnimatedIconSlot(
                slot = config.iconSlots.slot3,
                contentColor = config.contentColor
            )
            // Icon Slot 4
            AnimatedIconSlot(
                slot = config.iconSlots.slot4,
                contentColor = config.contentColor
            )

            // Custom composable slot
            AppBarSlotVisibility(visible = rightSlot != null) {
                rightSlot?.invoke()
            }
        }
    }
}

@Composable
private fun NavigationIcon(config: PopAppBarConfig) {
    config.navigationIcon?.let { iconName ->
        val resourceId = PopIconResources.getIconResourceId(iconName, IconStyle.Outline)
        resourceId?.let { id ->
            Image(
                painter = painterResource(id = id),
                contentDescription = "Navigation",
                modifier = Modifier
                    .size(24.dp)
                    .requiredSize(24.dp) // Prevent icon shrinking
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { config.onNavigationClick?.invoke() }
                    ),
                colorFilter = ColorFilter.tint(
                    config.navigationIconTint ?: config.contentColor
                )
            )
        }
    }
}


/**
 * Animated icon slot with configurable animation types
 */
@Composable
private fun AnimatedIconSlot(
    slot: AppBarIconSlot?,
    contentColor: Color
) {
    val isVisible = slot != null
    AppBarSlotVisibility(visible = isVisible) {
        slot?.let { iconSlot ->
            val rotation by animateFloatAsState(
                targetValue = when (iconSlot.animationType) {
                    IconAnimationType.Rotate90, IconAnimationType.RotateDissolve ->
                        if (isVisible) 0f else 90f
                    else -> 0f
                },
                animationSpec = tween(300),
                label = "iconRotation"
            )
            SlotIcon(iconSlot = iconSlot, contentColor = contentColor, rotation = rotation)
        }
    }
}

@Composable
private fun SlotIcon(
    iconSlot: AppBarIconSlot,
    contentColor: Color,
    rotation: Float
) {
    val resourceId = PopIconResources.getIconResourceId(iconSlot.icon, IconStyle.Outline)
    resourceId?.let { id ->
        Image(
            painter = painterResource(id = id),
            contentDescription = iconSlot.contentDescription,
            modifier = Modifier
                .size(20.dp)
                .requiredSize(20.dp) // Prevent icon shrinking
                .graphicsLayer { rotationZ = rotation }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { iconSlot.onClick() }
                ),
            colorFilter = ColorFilter.tint(
                iconSlot.iconTint ?: contentColor
            )
        )
    }
}

/**
 * Action icon button (legacy, for backward compatibility)
 */
@Composable
private fun ActionIconButton(
    iconName: IconName,
    onClick: () -> Unit,
    contentColor: Color,
    contentDescription: String?
) {
    val resourceId = PopIconResources.getIconResourceId(iconName, IconStyle.Outline)
    resourceId?.let { id ->
        Image(
            painter = painterResource(id = id),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onClick),
            colorFilter = ColorFilter.tint(contentColor),
            contentScale = ContentScale.Fit
        )
    }
}


