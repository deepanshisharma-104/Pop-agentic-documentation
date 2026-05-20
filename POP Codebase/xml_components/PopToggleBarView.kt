package com.pop.components.xml_components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.pop.compose_components.R

class PopToggleBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    enum class SelectedOption { FIRST, SECOND }

    private val backgroundView: FrameLayout
    private val containerLayout: LinearLayout
    private val firstOptionView: FrameLayout
    private val secondOptionView: FrameLayout
    private val firstOptionText: TextView
    private val secondOptionText: TextView

    var selectedOption: SelectedOption = SelectedOption.FIRST
        set(value) {
            field = value
            updateSelection()
        }

    private var onOptionSelectedListener: ((SelectedOption) -> Unit)? = null

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(56))

        // Create background
        backgroundView = FrameLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            background = GradientDrawable().apply {
                setColor(ContextCompat.getColor(context, R.color.neutral_2))
                cornerRadius = dpToPx(28).toFloat()
            }
        }

        // Create container
        containerLayout = LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.HORIZONTAL
            weightSum = 2f
        }

        // Create first option
        firstOptionView = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            setOnClickListener {
                selectedOption = SelectedOption.FIRST
                onOptionSelectedListener?.invoke(SelectedOption.FIRST)
            }

            setPadding(dpToPx(18), dpToPx(18), dpToPx(18), dpToPx(18))

        }

        firstOptionText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            setTextAppearance(R.style.TextAppearance_Flash_Body_B2)
            setTextColor(ContextCompat.getColor(context, R.color.font_primary_default))
        }

        // Create second option
        secondOptionView = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            setOnClickListener {
                selectedOption = SelectedOption.SECOND
                onOptionSelectedListener?.invoke(SelectedOption.SECOND)
            }
            setPadding(dpToPx(18), dpToPx(18), dpToPx(18), dpToPx(18))

        }

        secondOptionText = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            setTextAppearance(R.style.TextAppearance_Flash_Body_B2)
            setTextColor(ContextCompat.getColor(context, R.color.font_primary_default))
        }

        firstOptionView.addView(firstOptionText)
        secondOptionView.addView(secondOptionText)

        containerLayout.addView(firstOptionView)
        containerLayout.addView(secondOptionView)

        addView(backgroundView)
        addView(containerLayout)

        // Read attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FlashToggleBarView,
            0, 0
        ).apply {
            try {
                firstOptionText.text = getString(R.styleable.FlashToggleBarView_flashToggleFirstOption) ?: "First"
                secondOptionText.text = getString(R.styleable.FlashToggleBarView_flashToggleSecondOption) ?: "Second"
                val selectedIndex = getInt(R.styleable.FlashToggleBarView_flashToggleSelectedOption, 0)
                selectedOption = SelectedOption.values()[selectedIndex]
            } finally {
                recycle()
            }
        }

        updateSelection()
    }

    private fun updateSelection() {
        val selectedBackground = GradientDrawable().apply {
            setColor(ContextCompat.getColor(context, R.color.on_top_default))
            cornerRadius = dpToPx(24).toFloat()
        }

        val unselectedBackground = GradientDrawable().apply {
            setColor(ContextCompat.getColor(context, R.color.neutral_2))
            cornerRadius = dpToPx(24).toFloat()
        }

        when (selectedOption) {
            SelectedOption.FIRST -> {
                firstOptionView.background = selectedBackground
                secondOptionView.background = unselectedBackground
            }
            SelectedOption.SECOND -> {
                firstOptionView.background = unselectedBackground
                secondOptionView.background = selectedBackground
            }
        }
    }

    fun setFirstOptionText(text: String) {
        firstOptionText.text = text
    }

    fun setSecondOptionText(text: String) {
        secondOptionText.text = text
    }

    fun setOnOptionSelectedListener(listener: (SelectedOption) -> Unit) {
        onOptionSelectedListener = listener
    }

    private fun dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()
} 