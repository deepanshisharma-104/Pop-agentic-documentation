package com.pop.components.utils

import android.os.Build
import android.view.View
import android.view.WindowInsets

fun View.statusBarHeight(): Int {
    return when {
        // API 30+
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            rootWindowInsets
                ?.getInsets(WindowInsets.Type.statusBars())
                ?.top
                ?: 0
        }

        // API 24–29
        else -> {
            @Suppress("DEPRECATION")
            rootWindowInsets?.systemWindowInsetTop ?: 0
        }
    }
}


