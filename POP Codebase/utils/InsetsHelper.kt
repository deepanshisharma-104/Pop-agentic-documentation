package com.pop.components.utils

import android.content.res.Resources
import android.view.View
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object InsetsHelper {

    /**
     * Observes keyboard height using WindowInsets and returns
     * appropriate bottom padding through the callback.
     *
     * @param view The view to attach the insets listener to.
     * @param defaultPaddingDp The padding to apply when keyboard is hidden.
     * @param scaleFactor Optional scaling on keyboard height (ex: 1.0, 0.5)
     * @param onPaddingChanged Callback providing final paddingBottom in px.
     */
    fun observeKeyboardPadding(
        view: View,
        defaultPaddingDp: Int = 24,
        scaleFactor: Float = 1f,
        onPaddingChanged: (paddingBottom: Int) -> Unit
    ) {
        val defaultPaddingPx =
            (defaultPaddingDp * Resources.getSystem().displayMetrics.density).toInt()

        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val imeHeight = imeInsets.bottom

            val finalPadding =
                if (imeHeight > 0) (imeHeight * scaleFactor).toInt()
                else defaultPaddingPx

            onPaddingChanged(finalPadding)

            insets
        }
    }

    fun setNavigationBarsSafePadding(
        view: View,
    ) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val navBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())

            // Bottom navigation bar height
            val bottomPadding = navBarInsets.bottom
            v.setPadding(0, 0, 0, bottomPadding)
            insets
        }
    }

    @Composable
    fun getStatusBarHeight(): Dp {
        val insets = WindowInsets.statusBars
        val density = LocalDensity.current
        return with(density) { insets.getTop(this).toDp() }
    }

    @Composable
    fun getNavigationBarHeight(): Dp {
        val insets = WindowInsets.navigationBars
        val density = LocalDensity.current
        return with(density) { insets.getBottom(this).toDp() }
    }
}
