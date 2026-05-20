package com.pop.components

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.pop.compose_components.databinding.PopupMenuBinding

class CustomMenuPopup(
    private val context: Context,
    private val layoutInflater: LayoutInflater
) {

    private var popupWindow: PopupWindow? = null
    private val marginRightPx = (40 * context.resources.displayMetrics.density).toInt()
    private var popupBinding: PopupMenuBinding

    init {
        // Initialize the popup binding
        popupBinding = PopupMenuBinding.inflate(layoutInflater)
    }

    fun show(
        anchorView: View,
        holderPosition: Int,
        onDelete: (Int) -> Unit,
        onEdit: (Int) -> Unit,
        onMarkDefault: (Int) -> Unit
    ) {
        dismiss() // Dismiss any existing popup

        popupWindow = PopupWindow(
            popupBinding.root,
            350,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            isFocusable = true
        }

        // Get X and Y position for the popup
        val (x, y) = getXYPosition(popupBinding, anchorView)

        // Set up click listeners with lambdas
        popupBinding.menuDelete.setOnClickListener {
            onDelete(holderPosition)
            dismiss()
        }

        popupBinding.menuEdit.setOnClickListener {
            onEdit(holderPosition)
            dismiss()
        }

        popupBinding.menuDefault.setOnClickListener {
            onMarkDefault(holderPosition)
            dismiss()
        }

        popupWindow?.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y)
    }

    fun deleteOptionVisibleOrGone(visibility: Boolean = true) {
        popupBinding.menuDelete.isVisibleOrGone(visibility)
//        popupBinding.viewTopDivider.isVisibleOrGone(visibility)
    }

    fun editOptionVisibleOrGone(visibility: Boolean = true) {
        popupBinding.menuEdit.isVisibleOrGone(visibility)
    }

    fun defaultOptionVisibleOrGone(visibility: Boolean = true) {
        popupBinding.menuDefault.isVisibleOrGone(visibility)
        popupBinding.viewMiddleDivider.isVisibleOrGone(visibility)
    }

    private fun getXYPosition(
        popupBinding: PopupMenuBinding,
        anchorView: View
    ): Pair<Int, Int> {
        // Measure popup width
        popupBinding.root.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val popupWidth = popupBinding.root.measuredWidth

        // Get screen width
        val screenWidth = context.resources.displayMetrics.widthPixels

        // Calculate X so that popup is marginRightPx away from the right edge
        val x = screenWidth - popupWidth - marginRightPx

        // Get anchor's Y position on screen
        val anchorLocation = IntArray(2)
        anchorView.getLocationOnScreen(anchorLocation)
        val anchorY = anchorLocation[1]
        val anchorHeight = anchorView.height

        // Center vertically on anchor (or adjust as needed)
        val y = anchorY + (anchorHeight - popupBinding.root.measuredHeight) / 2
        return Pair(x, y)
    }

    fun dismiss() {
        popupWindow?.dismiss()
        popupWindow = null
    }
}



fun View.visible() {
    visibility = View.VISIBLE
}
fun View.invisible() {
    visibility = View.INVISIBLE
}
fun View.gone() {
    visibility = View.GONE
}

fun View.isVisibleOrGone(isVisible:Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}
