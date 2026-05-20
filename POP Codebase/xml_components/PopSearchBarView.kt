package com.pop.components.xml_components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.pop.compose_components.R

class PopSearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class Size { SMALL, BIG }

    private val searchIcon: ImageView
    private var editText: EditText? = null
    private val clearIcon: ImageView

    var text: String
        get() = editText?.text.toString()
        set(value) = editText!!.setText(value)

    private var size: Size = Size.SMALL
    private var onTextChangedListener: ((String) -> Unit)? = null
    private var onEditorActionListener: ((String) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        background = ContextCompat.getDrawable(context, R.drawable.bg_search_bar_radius_32)

        // Create search icon
        searchIcon = ImageView(context).apply {
            setImageResource(R.drawable.ic_search_white_hollow)
            setColorFilter(ContextCompat.getColor(context, R.color.neutral_11))

            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                width = resources.getDimensionPixelSize(R.dimen.spacing_24)
                height = resources.getDimensionPixelSize(R.dimen.spacing_24)
            }

            adjustViewBounds = true
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }

        // Create clear icon
        clearIcon = ImageView(context).apply {
            setImageResource(R.drawable.ic_close)
            setColorFilter(ContextCompat.getColor(context, R.color.font_primary_default))
            visibility = GONE
            setOnClickListener {
                editText?.setText("")
            }
        }

        // Create edit text
        editText = EditText(context).apply {
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f).apply {
                setMargins(dpToPx(12), 0, dpToPx(12), 0)
            }
            background = null
            setTextAppearance(R.style.TextAppearance_Flash_Body_B2)
            setTextColor(ContextCompat.getColor(context, R.color.font_primary_default))
            setHintTextColor(ContextCompat.getColor(context, R.color.neutral_11))
            isSingleLine = true

            imeOptions = EditorInfo.IME_ACTION_SEARCH

            addTextChangedListener { text ->
                val textStr = text?.toString() ?: ""
                onTextChangedListener?.invoke(textStr)
                updateIconColors(textStr)
                clearIcon.visibility = if (textStr.isNotEmpty()) VISIBLE else GONE
            }


            setOnFocusChangeListener { v, hasFocus ->
                this@PopSearchBarView.isSelected = hasFocus
                this@PopSearchBarView.isActivated = hasFocus
                this@PopSearchBarView.refreshDrawableState()
                v.invalidate()
            }

            // 👇 Handle Done action
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Hide the keyboard
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    onEditorActionListener?.invoke(v.text.toString())
                    v.clearFocus()
                    true
                } else {
                    false
                }
            }
        }

        addView(searchIcon)
        addView(editText)
        addView(clearIcon)

        // Read attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FlashSearchBarView,
            0, 0
        ).apply {
            try {
                editText?.hint = getString(R.styleable.FlashSearchBarView_flashSearchPlaceholder)
                    ?: "Send money to Mobile Number or UPI ID"
                val sizeIndex = getInt(R.styleable.FlashSearchBarView_flashSearchSize, 0)
                size = Size.values()[sizeIndex]
            } finally {
                recycle()
            }
        }

        updateSize()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isSelected) {
            mergeDrawableStates(drawableState, intArrayOf(android.R.attr.state_focused))
        }
        return drawableState
    }

    private fun updateSize() {
        val verticalPadding = when (size) {
            Size.SMALL -> dpToPx(0)
            Size.BIG -> dpToPx(0)
        }

        val iconSize = when (size) {
            Size.SMALL -> dpToPx(16)
            Size.BIG -> dpToPx(16)
        }

        val fontSize = when (size) {
            Size.SMALL -> 15f
            Size.BIG -> 15f
        }

        setPadding(dpToPx(16), verticalPadding, dpToPx(16), verticalPadding)

        searchIcon.layoutParams = LayoutParams(iconSize, iconSize)
        clearIcon.layoutParams = LayoutParams(iconSize, iconSize)

        editText?.textSize = fontSize
    }

    private fun updateIconColors(text: String) {
        val iconColor = if (text.isNotEmpty()) {
            ContextCompat.getColor(context, R.color.font_primary_default)
        } else {
            ContextCompat.getColor(context, R.color.neutral_7)
        }
        searchIcon.setColorFilter(iconColor)
    }

    fun setOnTextChangedListener(listener: (String) -> Unit) {
        onTextChangedListener = listener
    }

    fun setEditorChangeListener(listener: (String) -> Unit) {
        onEditorActionListener = listener
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        editText?.isEnabled = enabled
    }

    private fun dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()
} 