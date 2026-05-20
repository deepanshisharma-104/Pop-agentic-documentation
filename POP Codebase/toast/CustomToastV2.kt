package com.pop.components.toast

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import app.rive.runtime.kotlin.RiveAnimationView
import com.pop.components.isVisibleOrGone
import com.pop.compose_components.R
import com.pop.compose_components.databinding.ViewCustomToastV2Binding
import com.pop.compose_components.databinding.ViewCustomToastWithRiveBinding

class CustomToastV2 private constructor() {

    enum class Type {
        INFO, SUCCESS, ERROR, ERROR_BILL, POPTOAST
    }

    companion object {
        private var currentToastView: View? = null

        @JvmStatic
        fun show(
            context: Context,
            type: Type? = Type.INFO,
            title: String?,
            subtitle: String?
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = ViewCustomToastV2Binding.inflate(inflater)

            // Apply styling based on type
            when (type) {
                Type.INFO -> {
                    binding.toastContainer.setBackgroundResource(R.drawable.bg_solid_neutral_8_radius_16)
                    binding.toastIcon.setImageResource(R.drawable.ic_info_white)
                }
                Type.SUCCESS -> {
                    binding.toastContainer.setBackgroundResource(R.drawable.bg_green_9_radius_14_rectangular)
                    binding.toastIcon.setImageResource(R.drawable.ic_green_tick_white_bg_success)
                }
                Type.ERROR -> {
                    binding.toastContainer.setBackgroundResource(R.drawable.bg_solid_red_13_radius_16)
                    binding.toastIcon.setImageResource(R.drawable.ic_error_info_white)
                }
                Type.ERROR_BILL -> {
                    binding.toastContainer.setBackgroundResource(R.drawable.bg_solid_red_13_radius_16)
                    binding.toastIcon.setImageResource(R.drawable.ic_rcbp_bill)
                }

                Type.POPTOAST -> {
                    binding.toastContainer.setBackgroundResource(R.drawable.bg_solid_orange_9_radius_16)
                    binding.toastIcon.setImageResource(R.drawable.ic_poptoast_icon)
                }
                null -> {
                    binding.toastContainer.setBackgroundResource(R.drawable.bg_solid_orange_9_radius_16)
                    binding.toastIcon.setImageResource(R.drawable.ic_error_info_white)
                }
            }

            // Set text values
            binding.toastTitle.text = title
            binding.toastSubtitle.text = subtitle
            binding.toastSubtitle.isVisibleOrGone(!subtitle.isNullOrEmpty())

            // Show normal toast
            Toast(context).apply {
                view = binding.root
                duration = Toast.LENGTH_LONG
                setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 20)
                show()
            }
        }

        @JvmStatic
        fun showInfinite(
            activity: Activity,
            title: String?,
            subtitle: String?
        ) {
            showInfinite(
                activity = activity,
                title = title,
                subtitle = subtitle,
                backgroundRes = R.drawable.bg_solid_neutral_5_radius_16,
                showSuccessIcon = false,
                onDoneClick = null
            )
        }

        @JvmStatic
        fun showInfinite(
            activity: Activity,
            title: String?,
            subtitle: String?,
            backgroundRes: Int = R.drawable.bg_solid_neutral_5_radius_16,
            showSuccessIcon: Boolean = false,
            customIconRes: Int? = null,
            buttonText: String = "Done",
            onDoneClick: (() -> Unit)? = null
        ) {
            dismiss(activity) // clear any previous infinite toast

            val inflater = LayoutInflater.from(activity)
            val binding = ViewCustomToastWithRiveBinding.inflate(inflater)

            // Apply custom background
            binding.toastContainer.setBackgroundResource(backgroundRes)

            // Handle icon/animation visibility
            if (showSuccessIcon || customIconRes != null) {
                binding.riveView.visibility = View.GONE
                binding.successIcon.visibility = View.VISIBLE
                binding.doneButton.visibility = View.VISIBLE
                
                // Set custom icon if provided, otherwise use default success icon
                if (customIconRes != null) {
                    binding.successIcon.setImageResource(customIconRes)
                }
                
                // Set button text
                binding.doneButton.text = buttonText
                
                // Set up Done button click listener
                binding.doneButton.setOnClickListener {
                    onDoneClick?.invoke()
                    dismiss(activity)
                }
            } else {
                binding.riveView.visibility = View.VISIBLE
                binding.successIcon.visibility = View.GONE
                binding.doneButton.visibility = View.GONE
                
                // Play specific animation
                binding.riveView.setRiveResource(
                    R.raw.rcbp_polling_loader,
                    animationName = "Timeline 1",
                    autoplay = true
                )
            }

            // Set text values
            binding.toastTitle.text = title
            binding.toastSubtitle.text = subtitle
            binding.toastSubtitle.isVisibleOrGone(!subtitle.isNullOrEmpty())

            // Add to Activity root view
            val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                bottomMargin = 200
                marginStart = 16
                marginEnd = 16
            }

            rootView.addView(binding.root, layoutParams)
            currentToastView = binding.root
        }

        @JvmStatic
        fun dismiss(activity: Activity) {
            currentToastView?.let { view ->
                val rootView = activity.findViewById<ViewGroup>(android.R.id.content)
                rootView.removeView(view)
                currentToastView = null
            }
        }
    }
}
