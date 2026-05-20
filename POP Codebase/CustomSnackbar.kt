package com.pop.components

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.pop.compose_components.R

class CustomSnackbar private constructor() {

    companion object {
        fun show(
            context: Context,
            iconResId: Int,
            title: String,
            subtitle: String,
            duration: Long = 30000L,
            onUndo: () -> Unit,
            onClose: () -> Unit
        ) {

            val inflater = LayoutInflater.from(context)
            val snackbarView = inflater.inflate(R.layout.view_custom_snackbar, null)

            val icon = snackbarView.findViewById<ImageView>(R.id.snackbar_icon)
            val titleText = snackbarView.findViewById<TextView>(R.id.snackbar_title)
            val subtitleText = snackbarView.findViewById<TextView>(R.id.snackbar_subtitle)
            val undoBtn = snackbarView.findViewById<TextView>(R.id.snackbar_undo)
            val closeBtn = snackbarView.findViewById<ImageButton>(R.id.snackbar_close)


            icon.setImageResource(iconResId)
            titleText.text = title
            subtitleText.text = subtitle

            val parent = (context as? Activity)?.findViewById<ViewGroup>(android.R.id.content)
            if (parent == null) {
                Log.w("CustomSnackbar", "Unable to find root view to attach snackbar")
                return
            }

            // Undo callback
            undoBtn.setOnClickListener {
                onUndo()
                parent.removeView(snackbarView)
            }

            // Close callback
            closeBtn.setOnClickListener {
                onClose()
                parent.removeView(snackbarView)
            }

            // Add view to parent

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                setMargins(16.dpToPx(),40.dpToPx(), 16.dpToPx(), 160.dpToPx()) // adjust as needed
            }
            parent.addView(snackbarView, layoutParams)

//            parent.addView(snackbarView)

            // Auto-dismiss after `duration`
            Handler(Looper.getMainLooper()).postDelayed({
                if (parent.indexOfChild(snackbarView) != -1) {
                    parent.removeView(snackbarView)
                }
            }, duration)
        }
    }
}


fun Int.dpToPx(): Int {
    val displayMetrics = android.content.res.Resources.getSystem().displayMetrics
    return (this * displayMetrics.density).toInt()
}