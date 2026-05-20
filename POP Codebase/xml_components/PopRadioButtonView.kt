package com.pop.components.xml_components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.pop.compose_components.R

class PopRadioButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var isSelected: Boolean = false
    private var isEnabledView: Boolean = true

    private val radioView: FrameLayout
    private val labelText: TextView
    private val supportingText: TextView

    private var onSelectedListener: (() -> Unit)? = null

    init {
        orientation = HORIZONTAL
        gravity = Gravity.TOP
        setPadding(0, dpToPx(8), 0, dpToPx(8))

        // Create radio button circle
        radioView = FrameLayout(context).apply {
            layoutParams = LayoutParams(dpToPx(20), dpToPx(20)).apply {
                setMargins(0, 0, dpToPx(12), 0)
            }
        }

        // Create text container
        val textContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
            orientation = VERTICAL
        }

        // Create label text
        labelText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            setTextAppearance(R.style.TextAppearance_Flash_Body_B2)
        }

        // Create supporting text
        supportingText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dpToPx(2), 0, 0)
            }
            setTextAppearance(R.style.TextAppearance_Flash_Body_B3)
        }

        textContainer.addView(labelText)
        textContainer.addView(supportingText)

        addView(radioView)
        addView(textContainer)

        // Read attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FlashRadioButtonView,
            0, 0
        ).apply {
            try {
                labelText.text = getString(R.styleable.FlashRadioButtonView_flashRadioLabel) ?: ""
                val supportingTextValue = getString(R.styleable.FlashRadioButtonView_flashRadioSupportingText)
                if (supportingTextValue.isNullOrEmpty()) {
                    supportingText.visibility = GONE
                } else {
                    supportingText.text = supportingTextValue
                }
                isSelected = getBoolean(R.styleable.FlashRadioButtonView_flashRadioSelected, false)
                isEnabledView = getBoolean(R.styleable.FlashRadioButtonView_flashRadioEnabled, false)
            } finally {
                recycle()
            }
        }

        updateUI()

        setOnClickListener {
            if (isEnabledView) {
                isSelected = true
                updateUI()
                onSelectedListener?.invoke()
            }
        }
    }

    private fun updateUI() {
        // Label
        labelText.setTextColor(
            if (isEnabledView) ContextCompat.getColor(context, R.color.font_primary_default)
            else ContextCompat.getColor(context, R.color.font_primary_disabled)
        )

        // Supporting Text
        if (supportingText.visibility == VISIBLE) {
            val baseColor = ContextCompat.getColor(context, R.color.neutral_7)
            supportingText.setTextColor(
                if (isEnabledView) baseColor
                else ColorUtils.setAlphaComponent(baseColor, (255 * 0.3f).toInt())
            )
        }

        // Radio button circle
        val outerDrawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            color = null
            setStroke(
                dpToPx(2),
                if (!isEnabledView) {
                    val color = ContextCompat.getColor(context, R.color.neutral_7)
                    ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
                } else if (isSelected) {
                    ContextCompat.getColor(context, R.color.font_primary_default)
                } else {
                    ContextCompat.getColor(context, R.color.neutral_7)
                }
            )
        }
        radioView.background = outerDrawable
        radioView.removeAllViews()

        if (isSelected) {
            val innerCircle = View(context).apply {
                layoutParams = FrameLayout.LayoutParams(dpToPx(12), dpToPx(12)).apply {
                    gravity = Gravity.CENTER
                }
                background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(
                        if (isEnabledView) ContextCompat.getColor(context, R.color.font_primary_default)
                        else ContextCompat.getColor(context, R.color.font_primary_disabled)
                    )
                }
            }
            radioView.addView(innerCircle)
        }
    }

    fun setRadioButtonSelected(selected: Boolean) {
        isSelected = selected
        updateUI()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        isEnabledView = enabled
        updateUI()
    }

    fun isRadioButtonSelected(): Boolean = isSelected

    fun setOnSelectedListener(listener: () -> Unit) {
        onSelectedListener = listener
    }

    private fun dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()
} 