package com.pop.components

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.pop.compose_components.R


class CustomToast private constructor() {

    enum class Type {
        INFO, SUCCESS, ERROR, POPTOAST
    }

    companion object {
        fun show(
            context: Context,
            type: Type? = Type.INFO ,
            title: String?,
            subtitle: String?) {
            val inflater = LayoutInflater.from(context)
            val layout = inflater.inflate(R.layout.view_custom_toast, null)

            val container = layout.findViewById<LinearLayout>(R.id.toast_container)
            val icon = layout.findViewById<ImageView>(R.id.toast_icon)
            val titleText = layout.findViewById<TextView>(R.id.toast_title)
            val subtitleText = layout.findViewById<TextView>(R.id.toast_subtitle)

            // Apply styling based on type
            when (type) {
                Type.INFO -> {
                    container.setBackgroundResource(R.drawable.bg_solid_neutral_8_radius_16)
                    icon.setImageResource(R.drawable.ic_info_white)
                }

                Type.SUCCESS -> {
                    container.setBackgroundResource(R.drawable.bg_green_9_radius_14_rectangular)
                    icon.setImageResource(R.drawable.ic_green_tick_white_bg_success)
                }

                Type.ERROR -> {
                    container.setBackgroundResource(R.drawable.bg_solid_red_13_radius_16)
                    icon.setImageResource(R.drawable.ic_error_info_white)
                }

                Type.POPTOAST -> {
                    container.setBackgroundResource(R.drawable.bg_solid_orange_9_radius_16)
                    icon.setImageResource(R.drawable.ic_poptoast_icon)
                }

                else -> {
                    container.setBackgroundResource(R.drawable.bg_solid_red_13_radius_16)
                    container.setBackgroundResource(R.drawable.bg_solid_orange_9_radius_16)
                    icon.setImageResource(R.drawable.ic_error_info_white)
                }
            }

            titleText.text = title
            subtitleText.text = subtitle
            subtitleText.isVisibleOrGone(subtitle?.isNotEmpty() == true)

            // Make a full-width wrapper and add horizontal padding (16dp)
            val wrapper = FrameLayout(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                val horizontalPadding = 16.dpToPx()
                setPadding(horizontalPadding, 0, horizontalPadding, 0)
                addView(layout)
            }

            Toast(context).apply {
                view = wrapper
                this.duration = duration
                setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
                show()
            }
        }
    }
}