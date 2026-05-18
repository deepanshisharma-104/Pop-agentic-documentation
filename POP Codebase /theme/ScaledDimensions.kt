package com.pop.components.theme

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


object Responsive {

    private const val BASE_SCREEN_WIDTH_DP = 390f
    private const val BASE_DENSITY_DPI = 420f // you can tweak this if needed

    // Returns current display zoom level (1.0 = normal, >1 = zoomed in)
    fun getDisplayZoomLevel(): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi
        return densityDpi / BASE_DENSITY_DPI
    }

    // Scale dp based on screen width (respects zoom)
    @Composable
    fun scaledDp(baseDp: Dp): Dp {
        val configuration = LocalConfiguration.current
        val scaleFactor = configuration.screenWidthDp / BASE_SCREEN_WIDTH_DP
        return (baseDp.value * scaleFactor).dp
    }

    // Scale dp while ignoring zoom (looks same across zoom levels)
    @Composable
    fun scaledDpIgnoringZoom(baseDp: Dp): Dp {
        val configuration = LocalConfiguration.current
        val scaleFactor = configuration.screenWidthDp / BASE_SCREEN_WIDTH_DP
        return (baseDp.value / scaleFactor).dp
    }

    // Scale sp based on screen width only (Compose already applies fontScale)
    @Composable
    fun scaledSp(baseSp: TextUnit): TextUnit {
        val configuration = LocalConfiguration.current
        val scaleFactor = configuration.screenWidthDp / BASE_SCREEN_WIDTH_DP
        return (baseSp.value * scaleFactor).sp
    }

    // Scale sp while ignoring fontScale (stable text size)
    @Composable
    fun scaledSpIgnoringFontScale(baseSp: TextUnit): TextUnit {
        val configuration = LocalConfiguration.current
        val scaleFactor = configuration.screenWidthDp / BASE_SCREEN_WIDTH_DP
        val fontScale = configuration.fontScale
        return (baseSp.value * scaleFactor / fontScale).sp
    }

    // Optional combined scale: ignore both zoom and font scale
    @Composable
    fun scaledSpIgnoringAll(baseSp: TextUnit): TextUnit {
        val configuration = LocalConfiguration.current
        val scaleFactor = configuration.screenWidthDp / BASE_SCREEN_WIDTH_DP
        val fontScale = configuration.fontScale
        return (baseSp.value / scaleFactor / fontScale).sp
    }
}
