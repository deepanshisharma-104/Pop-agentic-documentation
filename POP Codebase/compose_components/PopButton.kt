package com.pop.components.compose_components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon // Added
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.FlashTheme
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.PopColors
import com.pop.components.theme.SurfaceColors
import com.pop.components.theme.TextColors
import com.pop.compose_components.R

@Deprecated("old Design System", replaceWith = ReplaceWith("PopButtonV2"))
@Composable
fun PopButton(
    text: CharSequence,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    variant: FlashButtonVariant = FlashButtonVariant.Primary,
    size: FlashButtonSize = FlashButtonSize.Large,
    isFullWidth: Boolean = true
) {
    val colors = FlashTheme.colors
    val dimensions = FlashTheme.dimensions
    val safeOnClick = rememberSafeClick(onSafeClick = onClick)

    val buttonHeight = when (size) {
        FlashButtonSize.Small -> dimensions.height.Small
        FlashButtonSize.Medium -> dimensions.height.Medium
        FlashButtonSize.Large -> dimensions.height.Large
    }

    val horizontalPadding = when (size) {
        FlashButtonSize.Small -> dimensions.padding.Default
        FlashButtonSize.Medium -> dimensions.padding.Default
        FlashButtonSize.Large -> dimensions.padding.Default
    }

    val fontSize = when (size) {
        FlashButtonSize.Small -> 13.sp
        FlashButtonSize.Medium -> 15.sp
        FlashButtonSize.Large -> 15.sp
    }

    val buttonColors = when (variant) {
        FlashButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = SurfaceColors.Button.Primary.Default,
            contentColor = TextColors.Primary.Default,
            disabledContainerColor = SurfaceColors.Button.Primary.Disabled,
            disabledContentColor = TextColors.Primary.Disabled
        )

        FlashButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = SurfaceColors.Button.Secondary.Default,
            contentColor = TextColors.Inverted.Default,
            disabledContainerColor = SurfaceColors.Button.Secondary.Disabled,
            disabledContentColor = PopColors.Misc.WhiteOpacity10
        )

        FlashButtonVariant.Tertiary -> ButtonDefaults.buttonColors(
            containerColor = SurfaceColors.Button.Tertiary.Default,
            contentColor = TextColors.Primary.Default,
            disabledContainerColor = SurfaceColors.Button.Tertiary.Disabled,
            disabledContentColor = PopColors.Misc.WhiteOpacity10
        )
    }
    val infiniteTransition =
        rememberInfiniteTransition(label = "spinner_label_button") // Changed label for uniqueness
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spinner_rotation_button" // Changed label for uniqueness
    )

    val inlineContentMap = mapOf(
        "coin" to InlineTextContent(
            Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_popcoin_white_spiral_orange_background),
                modifier = Modifier.fillMaxSize(),
                contentDescription = ""
            )
        }
    )

    Button(
        onClick = safeOnClick,
        modifier = modifier
            .then(if (isFullWidth) Modifier.fillMaxWidth() else Modifier)
            .defaultMinSize(minHeight = buttonHeight),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(dimensions.radius.Full),
        colors = buttonColors,
        contentPadding = PaddingValues(horizontal = horizontalPadding)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp), color = Color.White)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    leftIcon?.let {
                        Image(painter = painterResource(leftIcon), contentDescription = "Left Icon")
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    when (text) {
                        is AnnotatedString -> Text(
                            text = text,
                            inlineContent = inlineContentMap,
                            fontFamily = FlashTypography.figtree,
                            fontWeight = FontWeight.Bold,
                            fontSize = fontSize,
                            letterSpacing = (-0.02).sp,
                            textAlign = TextAlign.Center
                        )

                        else -> Text(
                            text = text.toString(),
                            fontFamily = FlashTypography.figtree,
                            fontWeight = FontWeight.Bold,
                            fontSize = fontSize,
                            letterSpacing = (-0.02).sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    rightIcon?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(painter = painterResource(rightIcon), contentDescription = "Right Icon")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PopButtonPreview() {
    PopButton(
        text = "Pop Button",
        onClick = {},
        variant = FlashButtonVariant.Primary,
        size = FlashButtonSize.Large,
        isFullWidth = true,
        isLoading = false,
        enabled = true
    )
}

@Preview
@Composable
fun PopImageButtonPreview() {
    PopButton(
        text = "Pop Button",
        leftIcon = R.drawable.ic_button_arrow,
        onClick = {},
        variant = FlashButtonVariant.Primary,
        size = FlashButtonSize.Large,
        isFullWidth = true,
        isLoading = false,
        enabled = false
    )
}

@Composable
fun PopIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    variant: FlashButtonVariant = FlashButtonVariant.Primary,
    size: FlashButtonSize = FlashButtonSize.Large,
    isFullWidth: Boolean = true
) {
    val colors = FlashTheme.colors
    val dimensions = FlashTheme.dimensions
    val safeOnClick = rememberSafeClick(onSafeClick = onClick)

    val buttonHeight = when (size) {
        FlashButtonSize.Small -> dimensions.height.Small
        FlashButtonSize.Medium -> dimensions.height.Medium
        FlashButtonSize.Large -> dimensions.height.Large
    }

    val horizontalPadding = when (size) {
        FlashButtonSize.Small -> dimensions.padding.Default
        FlashButtonSize.Medium -> dimensions.padding.Large
        FlashButtonSize.Large -> dimensions.padding.Large
    }

    val iconSizeDp = when (size) {
        FlashButtonSize.Small -> 18.dp
        FlashButtonSize.Medium -> 22.dp
        FlashButtonSize.Large -> 26.dp
    }

    val buttonColors = when (variant) {
        FlashButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = SurfaceColors.Button.Primary.Default,
            contentColor = TextColors.Primary.Default,
            disabledContainerColor = SurfaceColors.Button.Primary.Disabled,
            disabledContentColor = TextColors.Primary.Disabled
        )
        FlashButtonVariant.Secondary -> ButtonDefaults.buttonColors(
            containerColor = SurfaceColors.Button.Secondary.Default,
            contentColor = TextColors.Inverted.Default,
            disabledContainerColor = SurfaceColors.Button.Secondary.Disabled,
            disabledContentColor = PopColors.Misc.WhiteOpacity10
        )
        FlashButtonVariant.Tertiary -> ButtonDefaults.buttonColors(
            containerColor = SurfaceColors.Button.Tertiary.Default,
            contentColor = TextColors.Primary.Default,
            disabledContainerColor = SurfaceColors.Button.Tertiary.Disabled,
            disabledContentColor = PopColors.Misc.WhiteOpacity10
        )
    }

    Button(
        onClick = safeOnClick,
        modifier = modifier
            .then(if (isFullWidth) Modifier.fillMaxWidth() else Modifier)
            .defaultMinSize(minHeight = buttonHeight),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(dimensions.radius.Full),
        colors = buttonColors,
        contentPadding = PaddingValues(horizontal = horizontalPadding)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp), color = Color.White)
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_button_arrow),
                    contentDescription = contentDescription,
                    modifier = Modifier.size(iconSizeDp),
                    tint = buttonColors.contentColor // Use the resolved content color from ButtonDefaults
                )
            }
        }
    }
}

@Preview
@Composable
fun PopIconButtonPreview() {
    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, end = 16.dp)) {
        PopIconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = {

            },
            contentDescription = "Icon button",
            variant = FlashButtonVariant.Secondary,
            size = FlashButtonSize.Large,
            isFullWidth = false,
            isLoading = false,
            enabled = true
        )
    }
}

enum class FlashButtonVariant {
    Primary,
    Secondary,
    Tertiary
}

enum class FlashButtonSize {
    Small,
    Medium,
    Large
}


@Composable
fun rememberSafeClick(
    cooldownMillis: Long = 1000L, // adjust if needed
    onSafeClick: () -> Unit
): () -> Unit {
    var lastClickTime by remember { mutableStateOf(0L) } // works with getValue/setValue

    return {
        val now = System.currentTimeMillis()
        if (now - lastClickTime >= cooldownMillis) {
            lastClickTime = now
            onSafeClick()
        }
    }
}
