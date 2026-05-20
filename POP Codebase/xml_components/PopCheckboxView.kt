package com.pop.components.xml_components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.pop.compose_components.R

class PopCheckboxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class Variant { BLACK, ORANGE }

    private var isChecked: Boolean = false
    private var variant: Variant = Variant.BLACK
    private var isEnabledView: Boolean = true

    private val boxView: FrameLayout
    private val labelText: TextView
    private val supportingText: TextView

    private var onCheckedChangeListener: ((Boolean) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        gravity = Gravity.TOP
        setPadding(0, dpToPx(8), 0, dpToPx(8))

        // Create checkbox box
        boxView = FrameLayout(context).apply {
            layoutParams = LayoutParams(dpToPx(20), dpToPx(20)).apply {
                setMargins(0, 0, dpToPx(12), 0)
            }
            isClickable = false
            isFocusable = false
            background = null
            foreground = null
            setBackgroundColor(resources.getColor(R.color.neutral_12))

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

        addView(boxView)
        addView(textContainer)

        // Read attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FlashCheckboxView,
            0, 0
        ).apply {
            try {
                labelText.text = getString(R.styleable.FlashCheckboxView_flashCheckboxLabel) ?: ""
                val supportingTextValue = getString(R.styleable.FlashCheckboxView_flashCheckboxSupportingText)
                if (supportingTextValue.isNullOrEmpty()) {
                    supportingText.visibility = GONE
                } else {
                    supportingText.text = supportingTextValue
                }
                isChecked = getBoolean(R.styleable.FlashCheckboxView_flashCheckboxChecked, false)
                isEnabled = getBoolean(R.styleable.FlashCheckboxView_flashCheckboxEnabled, true)
                val variantIndex = getInt(R.styleable.FlashCheckboxView_flashCheckboxVariant, 0)
                variant = Variant.entries.toTypedArray()[variantIndex]
            } finally {
                recycle()
            }
        }

        updateUI()

        setOnClickListener {
            if (isEnabledView) {
                isChecked = !isChecked
                updateUI()
                onCheckedChangeListener?.invoke(isChecked)
            }
        }


        isClickable = true
        isFocusable = true
        background = null
        foreground = null
        setBackgroundResource(0)
        stateListAnimator = null

    }

    private fun updateUI() {
        // Label
        val baseColor = ContextCompat.getColor(context, R.color.neutral_7)

        labelText.setTextColor(
            if (isEnabledView) ContextCompat.getColor(context, R.color.font_primary_default) 
            else ColorUtils.setAlphaComponent(baseColor, (255 * 0.3f).toInt())
        )

        // Supporting Text
        if (supportingText.visibility == VISIBLE) {
            supportingText.setTextColor(
                if (isEnabledView) baseColor 
                else ColorUtils.setAlphaComponent(baseColor, (255 * 0.3f).toInt())
            )
        }

        // Checkbox box styles
        val drawable = GradientDrawable().apply {
            cornerRadius = dpToPx(2).toFloat()
            color = ColorStateList.valueOf(getBackgroundColor())
            
            if (!(variant == Variant.ORANGE && !isEnabledView)) {
                setStroke(dpToPx(1), getBorderColor())
            }
        }
        boxView.background = drawable
        boxView.removeAllViews()

        if (isChecked) {
            val icon = AppCompatImageView(context).apply {
                setImageResource(R.drawable.ic_check_white) //ic_check
                layoutParams = FrameLayout.LayoutParams(dpToPx(16), dpToPx(16)).apply {
                    gravity = Gravity.CENTER
                }
                setColorFilter(getCheckmarkTint(), PorterDuff.Mode.SRC_IN) // ✅ Added
                background = null
                setBackgroundDrawable(null) // 🔥 KEY LINE
                foreground = null
                isClickable = false
                isFocusable = false
                setPadding(0, 0, 0, 0)
            }
            boxView.addView(icon)
        } else if (variant == Variant.ORANGE) {
            val dot = View(context).apply {
                layoutParams = FrameLayout.LayoutParams(dpToPx(16), dpToPx(16)).apply {
                    gravity = Gravity.CENTER
                }
                background = GradientDrawable().apply {
                    setColor(ContextCompat.getColor(context, R.color.orange_1))
                    cornerRadius = dpToPx(2).toFloat()
                }
            }
            boxView.addView(dot)
        }
    }

    private fun getBackgroundColor(): Int {
        return when {
            variant == Variant.ORANGE -> {
                if(isEnabledView) {
                    if(isChecked) {
                        ContextCompat.getColor(context, R.color.orange_9)
                    } else {
                        ContextCompat.getColor(context, R.color.orange_3)
                    }

                } else {
                    val color = ContextCompat.getColor(context, R.color.orange_9)
                    ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
                    if(isChecked) {
                        ContextCompat.getColor(context, R.color.orange_3)
                    } else {
                        val color = ContextCompat.getColor(context, R.color.orange_9)
                        ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
                    }
                }
            }

            variant == Variant.BLACK -> {
                if(isEnabledView) {
                    if(isChecked) {
                        ContextCompat.getColor(context, R.color.font_primary_default)
                    } else {
//                        ContextCompat.getColor(context, R.color.neutral_2)
                        val color = ContextCompat.getColor(context, R.color.font_primary_default)
                        ColorUtils.setAlphaComponent(color, 18)
                    }

                } else {
                    val color = ContextCompat.getColor(context, R.color.font_primary_default)
                    if(isChecked) {
                        ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
                    } else {
                        ContextCompat.getColor(context, R.color.neutral_2)
                    }
                }
            }
//            !isEnabledView && variant == Variant.ORANGE -> {
//                val color = ContextCompat.getColor(context, R.color.orange_9)
//                ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
//            }
//            !isEnabledView && isChecked -> {
//                val color = ContextCompat.getColor(context, R.color.font_primary_default)
//                ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
//            }
//            isChecked -> ContextCompat.getColor(context, R.color.font_primary_default)
//            variant == Variant.ORANGE -> ContextCompat.getColor(context, R.color.orange_3)
            else -> ContextCompat.getColor(context, R.color.neutral_2)
        }
    }

    private fun getBorderColor(): Int {
        val color = ContextCompat.getColor(context, R.color.neutral_7)
        val disabledGrey = ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
        return when {
//            !isEnabledView -> {
//                val color = ContextCompat.getColor(context, R.color.neutral_7)
//                ColorUtils.setAlphaComponent(color, (255 * 0.3f).toInt())
//            }
//            isChecked -> ContextCompat.getColor(context, R.color.font_primary_default)
//            variant == Variant.ORANGE -> ContextCompat.getColor(context, R.color.orange_9)

            variant == Variant.ORANGE -> {
                if(isEnabledView) {
                    ContextCompat.getColor(context, R.color.orange_9)
                }
                else  {
                    ContextCompat.getColor(context, R.color.orange_3)
                }
            }

            variant == Variant.BLACK -> {
                if(isEnabledView) {
                    if(isChecked) {
                        ContextCompat.getColor(context, R.color.font_primary_default)
                    } else {
                        ContextCompat.getColor(context, R.color.neutral_11)
                    }
                }
                else  {
                    disabledGrey
                }
            }

            else -> ContextCompat.getColor(context, R.color.neutral_11)
        }
    }

    private fun getCheckmarkTint(): Int {
        return when (variant) {
            Variant.ORANGE -> ContextCompat.getColor(context, R.color.neutral_12)
            Variant.BLACK ->  ContextCompat.getColor(context, R.color.neutral_2)
        }
    }

    fun setChecked(checked: Boolean) {
        isChecked = checked
        updateUI()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        isEnabledView = enabled
        updateUI()
    }

    fun isChecked(): Boolean = isChecked

    fun setOnCheckedChangeListener(listener: (Boolean) -> Unit) {
        onCheckedChangeListener = listener
    }

    private fun dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()
}