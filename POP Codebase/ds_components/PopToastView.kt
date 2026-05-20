package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pop.components.compose_components.rememberSafeClick
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.IconSize
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopIconConfig
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.ui.config.EndCtaConfig
import com.pop.components.ui.config.ToastConfig
import com.pop.components.ui.config.ToastType
import com.pop.components.utils.popBackground

/**
 * Overload that accepts ToastConfig for easier integration
 */
@Composable
fun PopToastView(
    config: ToastConfig,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
) {
    PopToastView(
        text = config.title,
        modifier = modifier,
        type = config.type,
        startIconConfig = config.startIcon,
        closeIcon = config.showCloseIcon,
        onClose = onClose,
        endCta = config.endCta,
    )
}

/**
 * Helper function to get gradient based on toast type
 */
private fun getGradientForType(type: ToastType): PopGradient {
    return when (type) {
        ToastType.MESSAGE -> GradientPreset.ButtonBrandPrimaryLargeHorizontal.gradient
        ToastType.INFO -> GradientPreset.ButtonPrimaryInvertLarge.gradient
        ToastType.SUCCESS -> GradientPreset.GradientGreen.gradient
        ToastType.ERROR -> GradientPreset.GradientRed.gradient
    }
}

/**
 * Helper function to get text and icon color based on toast type
 */
private fun getContentColorForType(type: ToastType): Color {
    return when (type) {
        ToastType.INFO -> PopColor.WhiteBlack.Black
        else -> PopColor.WhiteBlack.White
    }
}

@Composable
private fun PopToastView(
    modifier: Modifier = Modifier,
    text: String,
    type: ToastType = ToastType.MESSAGE,
    startIconConfig: PopIconConfig? = null,
    closeIcon: Boolean = false,
    onClose: (() -> Unit)? = null,
    endCta: EndCtaConfig? = null
) {
    // Validation: closeIcon and endCta are mutually exclusive
    require(!(closeIcon && endCta != null)) {
        "closeIcon and endCta cannot be present at the same time"
    }

    val hasStartIcon = startIconConfig != null
    val gradient = getGradientForType(type)
    val contentColor = getContentColorForType(type)

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (content) = createRefs()
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .popBackground(
                    gradient = gradient
                )
                .border(width = 1.dp, color = BorderColor.PrimaryInvert.copy(alpha = 0.1f))
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            val (startImageRef, textRef, closeIconRef, endCtaRef) = createRefs()

            // Start Image/Icon (optional)
            if (hasStartIcon) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = SurfaceColor.PrimaryTransparent50,
                            shape = SquircleShape(smoothing = 1f)
                        )
                        .border(
                            width = (0.5).dp,
                            color = BorderColor.PrimaryTransparent30,
                            shape = SquircleShape(smoothing = 1f)
                        )
                        .constrainAs(startImageRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    PopVisualElement(
                        visualElement = startIconConfig.copy(
                            size = IconSize.Medium
                            // Use the tint from PopIconConfig, don't override it
                        ).toVisualElement(),
                        contentDescription = startIconConfig.contentDescription
                    )
                }
            }

            // Text (always present)
            Text(
                text = text,
                style = PopTypography.headingMedium,
                color = contentColor,
                textAlign = if (!hasStartIcon && endCta == null && !closeIcon) {
                    TextAlign.Center
                } else {
                    TextAlign.Start
                },
                maxLines = 2,
                modifier = Modifier
                    .constrainAs(textRef) {
                        start.linkTo(
                            if (hasStartIcon) startImageRef.end else parent.start,
                            margin = if (hasStartIcon) 12.dp else 0.dp
                        )
                        end.linkTo(
                            when {
                                closeIcon -> closeIconRef.start
                                endCta != null -> endCtaRef.start
                                else -> parent.end
                            },
                            margin = when {
                                closeIcon -> 12.dp
                                endCta != null -> 12.dp
                                else -> 0.dp
                            }
                        )
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }
            )

            // Close Icon (optional, mutually exclusive with endCta)
            if (closeIcon) {
                val safeOnClose = rememberSafeClick(onSafeClick = { onClose?.invoke() })
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = safeOnClose)
                        .constrainAs(closeIconRef) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = contentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // End CTA (optional, mutually exclusive with closeIcon)
            endCta?.let { cta ->
                val safeOnClick = rememberSafeClick(onSafeClick = cta.onClick)
                PopButtonV2(
                    text = cta.text,
                    onClick = safeOnClick,
                    variant = ButtonVariant.Secondary,
                    size = ButtonSize.XSmall,
                    modifier = Modifier
                        .constrainAs(endCtaRef) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xff0D0D0D, showBackground = true)
@Composable
private fun PopToastViewTest(){
    Box(Modifier.background(Color.Black).fillMaxSize().padding(top = 100.dp)) {
        PopToastView(
            modifier = Modifier,
            "Details updated successfully",
            ToastType.ERROR,
            null,
            false,
            { },
            null
        )
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xff0D0D0D, showBackground = true)
@Composable
private fun PopToastViewTestSuccess(){
    Box(Modifier.background(Color.Black).fillMaxSize().padding(top = 100.dp)) {
        PopToastView(
            modifier = Modifier,
            "Details updated successfully",
            ToastType.SUCCESS,
            null,
            false,
            { },
            null
        )
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xff0D0D0D, showBackground = true)
@Composable
private fun PopToastViewTestWhite(){
    Box(Modifier.background(Color.Black).fillMaxSize().padding(top = 100.dp)) {
        PopToastView(
            modifier = Modifier,
            "Details updated successfully",
            ToastType.INFO,
            null,
            false,
            { },
            null
        )
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xff0D0D0D, showBackground = true)
@Composable
private fun PopToastViewTestOrange(){
    Box(Modifier.background(Color.Black).fillMaxSize().padding(top = 100.dp)) {
        PopToastView(
            modifier = Modifier,
            "Details updated successfully",
            ToastType.MESSAGE,
            null,
            false,
            { },
            null
        )
    }
}
