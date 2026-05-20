package com.pop.components.xml_components

import android.content.Context
import android.content.res.ColorStateList
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pop.compose_components.R

class PopPhoneInputFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textInputLayout: TextInputLayout
    private val textInputEditText: TextInputEditText
    private val errorTextView: TextView
    private val prefixTextView: TextView

    private  var inputContainer: LinearLayout? = null

    fun isValidated(): Boolean {
        return TextUtils.isEmpty(errorMessage) && !TextUtils.isEmpty(text)
    }


    fun setSelection(index: Int) {
        textInputEditText.setSelection(index)
    }

    var text: String
        get() = textInputEditText.text?.toString() ?: ""
        set(value) { textInputEditText.setText(value) }

    var isError: Boolean = false
        set(value) {
            field = value
            updateErrorState()
        }

    var errorMessage: String? = null
        set(value) {
            field = value
            if(value?.isNotEmpty() == true) {
                this.isError = true
            }else {
                this.isError = false
            }
            updateErrorState()
        }

    private var onTextChangedListener: ((String) -> Unit)? = null

    init {
        orientation = VERTICAL

        // Create container for prefix and input with unified background
         inputContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL

            // Apply background and padding to entire container
            background = ContextCompat.getDrawable(context, R.drawable.bg_input_field)
            setPadding(16, 8, 16, 26)
        }

        // Create prefix text
        prefixTextView = TextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(0, 8, 8, 0)
            }
            text = "+91 |"
            setTextColor(ContextCompat.getColor(context, R.color.neutral_11))
            textSize = 15f // Match the input field text size
        }

        // Create TextInputLayout without its own background
        textInputLayout = TextInputLayout(context).apply {
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)

            // Remove TextInputLayout's own styling - container handles it
            setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_NONE)
            boxStrokeWidth = 0
            boxStrokeWidthFocused = 0
            // Copy hint color approach from FlashLabeledInputFieldView
            val greyColor = ContextCompat.getColor(context, R.color.neutral_11)
            val greyColorStateList = ColorStateList.valueOf(greyColor)
            setHintTextColor(greyColorStateList)

            defaultHintTextColor = greyColorStateList

            isErrorEnabled = false
        }

        // Create TextInputEditText
        textInputEditText = TextInputEditText(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            // Copy the exact approach from FlashInputFieldView that works
            hint = "Drop your number here"
            setTextAppearance(R.style.TextAppearance_Flash_InputField)
            // Explicitly set hint color to grey (after text appearance to override any style)
            setHintTextColor(ContextCompat.getColor(context, R.color.neutral_11))
            background = null
            inputType = InputType.TYPE_CLASS_PHONE

            // Center align text vertically, left align horizontally
            gravity = Gravity.CENTER_VERTICAL or Gravity.START

            // Add filter to only allow digits
            filters = arrayOf(object : InputFilter {
                override fun filter(
                    source: CharSequence?,
                    start: Int,
                    end: Int,
                    dest: Spanned?,
                    dstart: Int,
                    dend: Int
                ): CharSequence? {
                    return if (source?.all { it.isDigit() } == true) null else ""
                }
            })

            addTextChangedListener { text ->
                onTextChangedListener?.invoke(text?.toString() ?: "")
                inputContainer?.let { updateContainerState(it) }
            }

            setOnFocusChangeListener { _, hasFocus ->
                inputContainer?.let { updateContainerState(it) }
            }
        }

        // Create error TextView
        errorTextView = TextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(16, 8, 16, 0)
            }
            setTextAppearance(R.style.TextAppearance_Flash_Body_B3)
            setTextColor(ContextCompat.getColor(context, R.color.font_accent_default))
            visibility = GONE
        }

        textInputLayout.addView(textInputEditText)
        inputContainer?.addView(prefixTextView)
        inputContainer?.addView(textInputLayout)

        addView(inputContainer)
        addView(errorTextView)

        // Adjust initial padding based on hint state
        textInputLayout.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                textInputLayout.viewTreeObserver.removeOnPreDrawListener(this)
                adjustPaddingBasedOnHint()
                return true
            }
        })
    }

    private fun adjustPaddingBasedOnHint() {
        val isFloating = textInputEditText.hasFocus() || !textInputEditText.text.isNullOrEmpty()
        val top = if (isFloating) 16 else 8//8
        val bottom = 26//16//8

        textInputEditText.setPadding(
            textInputEditText.paddingLeft,
            top,
            textInputEditText.paddingRight,
            bottom
        )
    }

    private fun updateContainerState(container: LinearLayout) {
        val drawable = if (isError) {
            ContextCompat.getDrawable(context, R.drawable.bg_input_field_error)
        } else if (textInputEditText.hasFocus()) {
            ContextCompat.getDrawable(context, R.drawable.bg_input_field_focused)
        } else {
            ContextCompat.getDrawable(context, R.drawable.bg_input_field)
        }
        container.background = drawable
    }

    private fun updateErrorState() {
        if (isError && !errorMessage.isNullOrEmpty()) {
            errorTextView.text = errorMessage
            errorTextView.visibility = VISIBLE
        } else {
            errorTextView.visibility = GONE
        }

        // Update container background
        val container = getChildAt(0) as LinearLayout
        updateContainerState(container)
    }

    fun setOnTextChangedListener(listener: (String) -> Unit) {
        onTextChangedListener = listener
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        textInputLayout.isEnabled = enabled
        textInputEditText.isEnabled = enabled
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Force hint color when view is attached to window (copy from FlashInputFieldView approach)
        textInputEditText.setHintTextColor(ContextCompat.getColor(context, R.color.neutral_11))
        // Force initial padding when attached
        if (textInputEditText.hasFocus() || !textInputEditText.text.isNullOrEmpty()) {
            inputContainer?.setPadding(20, 20, 20, 26)
        } else {
            inputContainer?.setPadding(20, 0, 20, 26)
        }
        inputContainer?.requestLayout()
    }

    fun addTextWatcher(watcher: android.text.TextWatcher) {
        textInputEditText.addTextChangedListener(watcher)
    }
}