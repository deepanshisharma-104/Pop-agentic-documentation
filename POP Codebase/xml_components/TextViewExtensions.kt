package com.pop.components.xml_components

import android.graphics.Paint
import android.widget.TextView
import com.pop.compose_components.R

/**
 * Extension function to apply underline decoration to TextView.
 * Useful for link styles where android:textDecoration is not available below API 34.
 * 
 * Usage:
 * ```kotlin
 * textView.applyUnderline()
 * ```
 */
fun TextView.applyUnderline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

/**
 * Extension function to remove underline decoration from TextView.
 */
fun TextView.removeUnderline() {
    paintFlags = paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
}

/**
 * Helper function to apply underline for Pop Link text appearance styles.
 * Call this after setting textAppearance to a link style.
 * 
 * Usage:
 * ```kotlin
 * textView.setTextAppearance(R.style.TextAppearance_Pop_Link_Large)
 * textView.applyLinkUnderline() // Apply underline for link style
 * ```
 */
fun TextView.applyLinkUnderline() {
    applyUnderline()
}

