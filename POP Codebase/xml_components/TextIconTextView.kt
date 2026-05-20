package com.pop.components.xml_components

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.pop.compose_components.R

class TextIconTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val startText: TextView
    private val endText: TextView
    private val centerIcon: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_text_icon_text, this, true)
        startText = findViewById(R.id.textStart)
        endText = findViewById(R.id.textEnd)
        centerIcon = findViewById(R.id.centerIcon)
    }

    fun setStartText(text: String?) {
        startText.text = text
    }
    fun setEndText(text: String?) {
        endText.text = text
    }

    fun setIcon(@DrawableRes drawableRes: Int) {
        centerIcon.setImageResource(drawableRes)
    }

    fun setStartBold(isBold: Boolean) {
        startText.setTypeface(null, if (isBold) Typeface.BOLD else Typeface.NORMAL)
    }

    fun setEndBold(isBold: Boolean) {
        endText.setTypeface(null, if (isBold) Typeface.BOLD else Typeface.NORMAL)
    }
}

