package com.pop.components

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.pop.components.xml_components.PopTextView
import com.pop.compose_components.R
import com.pop.compose_components.databinding.GenericMenuPopupBinding

/**
 * Data class representing a menu item in the popup
 */
data class MenuItem(
    val text: String,
    val textColor: Int? = null,
    val iconRes: Int? = null,
    val iconTint: Int? = null,
    val onClick: () -> Unit,
    val isDestructive: Boolean = false // If true, uses red text color
)

/**
 * Generic popup menu that allows dynamic menu items to be passed at call site
 */
class GenericMenuPopup(
    private val context: Context,
    private val layoutInflater: LayoutInflater
) {

    private var popupWindow: PopupWindow? = null
    private var popupBinding: GenericMenuPopupBinding

    init {
        popupBinding = GenericMenuPopupBinding.inflate(layoutInflater)
    }

    /**
     * Shows the popup menu with the provided menu items
     * @param anchorView The view to anchor the popup to
     * @param menuItems List of menu items to display
     */
    fun show(
        anchorView: View,
        menuItems: List<MenuItem>
    ) {
        dismiss() // Dismiss any existing popup

        if (menuItems.isEmpty()) {
            return
        }

        // Clear existing menu items
        popupBinding.menuContainer.removeAllViews()

        // Create menu items dynamically
        menuItems.forEachIndexed { index, item ->
            val menuItemView = createMenuItemView(item)
            popupBinding.menuContainer.addView(menuItemView)

            // Add divider between items (except after last item)
            if (index < menuItems.size - 1) {
                val divider = createDivider()
                popupBinding.menuContainer.addView(divider)
            }
        }

        // Measure the popup to get proper dimensions
        popupBinding.root.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        popupWindow = PopupWindow(
            popupBinding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            isFocusable = true
        }

        // Get X and Y position for the popup
        val (x, y) = getXYPosition(popupBinding, anchorView)

        popupWindow?.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y)
    }

    private fun createMenuItemView(item: MenuItem): TextView {
        val textView = PopTextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                // Set padding
                val paddingHorizontal = (12 * context.resources.displayMetrics.density).toInt()
                val paddingVertical = (8 * context.resources.displayMetrics.density).toInt()
                setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
            }

            text = item.text
            gravity = android.view.Gravity.CENTER_VERTICAL

            // Set text color
            val textColorRes = when {
                item.isDestructive -> R.color.red_13
                item.textColor != null -> item.textColor
                else -> R.color.neutral_12
            }
            setTextColor(ContextCompat.getColor(context, textColorRes))

            // Set text style - using B3 style
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 13f)
            
            // Set font to medium
            typeface = ResourcesCompat.getFont(context, R.font.figtree_medium)

            // Set icon if provided
            item.iconRes?.let { iconRes ->
                val iconTint = item.iconTint ?: textColorRes
                val drawable = ContextCompat.getDrawable(context, iconRes)?.apply {
                    setTint(ContextCompat.getColor(context, iconTint))
                }
                setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
                
                val drawablePadding = (12 * context.resources.displayMetrics.density).toInt()
                compoundDrawablePadding = drawablePadding
            }

            // Set click listener
            isClickable = true
            isFocusable = true
            setOnClickListener {
                item.onClick()
                dismiss()
            }
        }
        return textView
    }

    private fun createDivider(): View {
        return View(context).apply {
            // Divider should have margins matching the horizontal padding of menu items (12dp)
            val horizontalPadding = (12 * context.resources.displayMetrics.density).toInt()
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (1 * context.resources.displayMetrics.density).toInt()
            ).apply {
                marginStart = horizontalPadding
                marginEnd = horizontalPadding
            }
            setBackgroundColor(ContextCompat.getColor(context, R.color.white_opacity_10))
        }
    }

    private fun getXYPosition(
        popupBinding: GenericMenuPopupBinding,
        anchorView: View
    ): Pair<Int, Int> {
        // Measure popup width and height with WRAP_CONTENT
        popupBinding.root.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val popupWidth = popupBinding.root.measuredWidth
        val popupHeight = popupBinding.root.measuredHeight

        // Get anchor's position - use getLocationInWindow for DialogFragments
        // This gives window-relative coordinates which PopupWindow.showAtLocation expects
        val anchorLocation = IntArray(2)
        anchorView.getLocationInWindow(anchorLocation)
        val anchorX = anchorLocation[0]
        val anchorY = anchorLocation[1]
        
        val anchorWidth = anchorView.width
        val anchorHeight = anchorView.height

        // Get window dimensions for proper positioning
        val screenWidth = context.resources.displayMetrics.widthPixels
        val marginFromRight = (16 * context.resources.displayMetrics.density).toInt()
        
        // Calculate X position: align popup's right edge with anchor's right edge
        var x = anchorX + anchorWidth - popupWidth
        
        // Ensure it doesn't go off-screen to the left or right
        if (x < marginFromRight) {
            x = marginFromRight // Too far left, move it right
        } else if (x + popupWidth > screenWidth - marginFromRight) {
            x = screenWidth - popupWidth - marginFromRight // Too far right, move it left
        }

        // Position popup directly below the anchor with a small gap (4dp)
        val gapBelowAnchor = (4 * context.resources.displayMetrics.density).toInt()
        val y = anchorY + anchorHeight + gapBelowAnchor
        
        return Pair(x, y)
    }

    fun dismiss() {
        popupWindow?.dismiss()
        popupWindow = null
    }
}

