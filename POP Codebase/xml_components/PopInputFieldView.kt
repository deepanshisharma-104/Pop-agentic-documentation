package com.pop.components.xml_components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Handler
import android.text.InputFilter
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pop.compose_components.R

open class PopInputFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class InputType { TEXT, EMAIL, PASSWORD, NUMBER, PHONE }

    protected val textInputLayout: TextInputLayout
    val textInputEditText: TextInputEditText
    protected var inputContainer: LinearLayout
    private val errorTextView: TextView
    private val successTextView: TextView

//
//    var text: String
//        get() = textInputEditText.text?.toString() ?: ""
//        set(value) {
//            textInputEditText.setText(value)
//        }

    enum class InputSize { NORMAL, SMALL }

    var inputSize: InputSize = InputSize.NORMAL
        set(value) {
            field = value
            applySize()
        }

    private fun applySize() {
        when (inputSize) {
            InputSize.NORMAL -> {
                inputContainer.setPadding(20, 12, 20, 12)

                textInputEditText.typeface = ResourcesCompat.getFont(context, R.font.figtree_medium)
                textInputEditText.setTextAppearance(R.style.TextAppearance_Flash_InputField)

                errorTextView.setTextAppearance(R.style.TextAppearance_Flash_Body_B3)
                successTextView.setTextAppearance(R.style.TextAppearance_Flash_Body_B3)

                successTextView.compoundDrawablePadding = 12
                resizeTickDrawable(successTextView, 16)
            }
            InputSize.SMALL -> {
                inputContainer.setPadding(16, 8, 16, 8) // Consistent for small
                textInputEditText.typeface = ResourcesCompat.getFont(context, R.font.figtree_medium)

                textInputEditText.setTextAppearance(R.style.TextAppearance_Flash_InputField)
                errorTextView.setTextAppearance(R.style.TextAppearance_Flash_Body_B3)
                successTextView.setTextAppearance(R.style.TextAppearance_Flash_Body_B3)

                successTextView.compoundDrawablePadding = 8
                resizeTickDrawable(successTextView, 12)
            }
        }
    }

    private fun resizeTickDrawable(textView: TextView, dp: Int) {
        val size = (dp * resources.displayMetrics.density).toInt()
        val tickDrawable = ContextCompat.getDrawable(context, R.drawable.ic_check_white)?.apply {
            setTint(ContextCompat.getColor(context, R.color.green_9))
            setBounds(0, 0, size, size)
        }
        textView.setCompoundDrawables(tickDrawable, null, null, null)
    }

    fun isValidated(): Boolean {
        return TextUtils.isEmpty(errorMessage) && !TextUtils.isEmpty(text)
    }

    var text: CharSequence
        get() = textInputEditText.text ?: ""
        set(value) {
            textInputEditText.setText(value)
        }

    var isError: Boolean = false
        set(value) {
            field = value
            updateErrorState()
        }

    var errorMessage: String? = null
        set(value) {
            field = value
            if (value?.isNotEmpty() == true) {
                this.isError = true
            }
            updateErrorState()
        }

    var successMessage: String? = null
        set(value) {
            field = value
            updateSuccessState()
        }

    private fun updateSuccessState() {
        if (!successMessage.isNullOrEmpty() && !isError) {
            successTextView.text = successMessage
            successTextView.visibility = VISIBLE
            successTextView.setTextColor(ContextCompat.getColor(context, R.color.green_9)) // green
        } else {
            successTextView.visibility = GONE
            if(!isError) {
                successTextView.setTextColor(ContextCompat.getColor(context, R.color.font_primary_default))
            }
        }
    }

    private var onTextChangedListener: ((String) -> Unit)? = null

    private var textStoppedHandler: Handler? = null
    private var textStoppedRunnable: Runnable? = null


    init {
        orientation = VERTICAL

        // Create container for input with unified background (similar to phone input)
        inputContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            // Apply background and padding to entire container
            background = ContextCompat.getDrawable(context, R.drawable.bg_input_field_)
            // Use different padding for regular vs labeled inputs (will be adjusted in subclass if needed)
            setPadding(20, 12, 20, 12)
            if(inputSize == InputSize.SMALL) {
                setPadding(16, 0, 16, 4)
            }
        }

        // Create TextInputLayout without its own background
        textInputLayout = TextInputLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER_VERTICAL
            }

            //setTextAppearance(null,R.style.TextAppearance_Flash_InputField)

            // Set Figtree font
            typeface = ResourcesCompat.getFont(context, R.font.figtree_medium)

            // Remove TextInputLayout's own styling - container handles it
            boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_NONE
            boxStrokeWidth = 0
            boxStrokeWidthFocused = 0
            hintTextColor = ContextCompat.getColorStateList(context, R.color.neutral_11)
            isErrorEnabled = false
        }


        // Create TextInputEditText
        textInputEditText = TextInputEditText(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_VERTICAL
            }
            setTextAppearance(R.style.TextAppearance_Flash_InputField)
            // Explicitly set hint color to grey (after text appearance to override any style)
            setHintTextColor(ContextCompat.getColor(context, R.color.neutral_11))
            background = null

            // Center align text vertically, left align horizontally
            gravity = Gravity.CENTER_VERTICAL or Gravity.START

            // Remove any underline or cursor drawable that might appear
            highlightColor = Color.TRANSPARENT
            isFocusable = true
            isFocusableInTouchMode = true

            addTextChangedListener { text ->
                onTextChangedListener?.invoke(text?.toString() ?: "")
                updateContainerState(inputContainer)
                adjustPaddingBasedOnHint()
            }

        }

        // Create error TextView
        errorTextView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(16, 8, 16, 0)
                }
            setTextAppearance(R.style.TextAppearance_Flash_Body_B3)
            setTextColor(ContextCompat.getColor(context, R.color.red_13))
            visibility = GONE
        }
        // Create success TextView
        successTextView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(16, 8, 16, 0)
                }
            setTextAppearance(R.style.TextAppearance_Flash_Body_B3)
            setTextColor(ContextCompat.getColor(context, R.color.green_9)) // your green color
            // Set tick icon on the start (drawableLeft for LTR layouts)
            val tickDrawable = ContextCompat.getDrawable(context, R.drawable.ic_check_white)?.apply {
                setTint(ContextCompat.getColor(context, R.color.green_9)) // tint the white tick green
                // Resize the drawable manually (e.g. 16dp × 16dp)
                val size = (10 * resources.displayMetrics.density).toInt()
                setBounds(0, 0, size, size)
            }
            setCompoundDrawablesWithIntrinsicBounds(tickDrawable, null, null, null)

            // Add spacing between icon and text
            compoundDrawablePadding = 12
            visibility = GONE
        }

        textInputLayout.addView(textInputEditText)
        inputContainer.addView(textInputLayout)

        addView(inputContainer)
        addView(errorTextView)
        addView(successTextView)

        // Read attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FlashInputFieldView,
            0, 0
        ).apply {
            try {
                // For regular FlashInputFieldView, don't show any hint/label
                textInputLayout.hint = null
                textInputEditText.hint =
                    getString(R.styleable.FlashInputFieldView_flashInputPlaceholder)
                val maxLength = getInt(R.styleable.FlashInputFieldView_maxLength, 0)
                if (maxLength > 0) {
                    setEditTextMaxLength(maxLength)
                }

                isError = getBoolean(R.styleable.FlashInputFieldView_flashInputError, false)
                errorMessage = getString(R.styleable.FlashInputFieldView_flashInputErrorMessage)

                val inputTypeIndex = getInt(R.styleable.FlashInputFieldView_flashInputType, 0)
                setInputType(InputType.entries[inputTypeIndex])

                // ✅ Apply text color if provided
                val inputTextColor = getColor(
                    R.styleable.FlashInputFieldView_flashInputTextColor,
                    ContextCompat.getColor(context, R.color.font_primary_default) // fallback
                )
                textInputEditText.setTextColor(inputTextColor)

                // ✅ Apply hint color if provided
                val hintColor = getColor(
                    R.styleable.FlashInputFieldView_flashInputHintColor,
                    ContextCompat.getColor(context, R.color.text_secondary) // fallback
                )
                textInputEditText.setHintTextColor(hintColor)
                textInputLayout.hintTextColor = ColorStateList.valueOf(hintColor)
                val inputSizeIndex = getInt(R.styleable.FlashInputFieldView_flashInputSize, 0)
                inputSize = InputSize.values()[inputSizeIndex]
            } finally {
                recycle()
            }
        }

        // Adjust initial padding based on hint state
        textInputLayout.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                textInputLayout.viewTreeObserver.removeOnPreDrawListener(this)
                adjustPaddingBasedOnHint()
                return true
            }
        })

    }

    fun adjustPaddingBasedOnHint() {
        /*val isFloating = textInputEditText.hasFocus() || !textInputEditText.text.isNullOrEmpty()
        var top = if (isFloating) 8 else 8
        var bottom = if (inputSize == InputSize.SMALL) 16 else 16

        if (inputSize == InputSize.SMALL) {
            top = 0
            bottom = 0
        }

        textInputEditText.setPadding(
            textInputEditText.paddingLeft,
            top,
            textInputEditText.paddingRight,
            bottom
        )*/
    }


    private fun setInputType(type: InputType) {
        textInputEditText.inputType = when (type) {
            InputType.TEXT -> android.text.InputType.TYPE_CLASS_TEXT
            InputType.EMAIL -> android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            InputType.PASSWORD -> android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            InputType.NUMBER -> android.text.InputType.TYPE_CLASS_NUMBER
            InputType.PHONE -> android.text.InputType.TYPE_CLASS_PHONE
        }
    }

    protected fun updateContainerState(container: LinearLayout) {
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
            errorTextView.setTextColor(ContextCompat.getColor(context, R.color.red_13)) // red
        } else {
            errorTextView.visibility = GONE
            errorTextView.setTextColor(ContextCompat.getColor(context, R.color.font_primary_default)) // default
        }

        // Update container background
        val container = getChildAt(0) as LinearLayout
        updateContainerState(inputContainer)
    }

    fun setOnTextChangedListener(listener: (String) -> Unit) {
        onTextChangedListener = listener
    }

    fun setAfterTextStoppedChangedListener(delayMillis: Long = 200, listener: (String) -> Unit) {
        textStoppedHandler = Handler(context.mainLooper)

        textInputEditText.addTextChangedListener { editable ->
            textStoppedRunnable?.let { textStoppedHandler?.removeCallbacks(it) }

            textStoppedRunnable = Runnable {
                listener.invoke(editable?.toString() ?: "")
            }

            textStoppedHandler?.postDelayed(textStoppedRunnable!!, delayMillis)
            adjustPaddingBasedOnHint()
        }
    }

    fun setDigits(digits: String) {
        textInputEditText.setKeyListener(DigitsKeyListener.getInstance(digits))
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        textInputLayout.isEnabled = enabled
        textInputEditText.isEnabled = enabled
    }

    fun setEditTextMaxLength(length: Int) {
        textInputEditText?.filters = arrayOf(InputFilter.LengthFilter(length))
    }

    fun addTextWatcher(watcher: android.text.TextWatcher) {
        textInputEditText.addTextChangedListener(watcher)
    }

    fun setImeOptionType(imeOptionType: Int) {
        textInputEditText.imeOptions = imeOptionType
    }

    fun setOnEditorActionListener(callback : (Boolean) -> Unit = {}) {
        textInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                callback.invoke(true)
                true
            } else false
        }
    }

    fun setReadOnly(readOnly: Boolean) {
        textInputEditText.apply {
            isFocusable = !readOnly
            isFocusableInTouchMode = !readOnly
            isClickable = !readOnly
            isCursorVisible = !readOnly
            keyListener = if (readOnly) null else DigitsKeyListener.getInstance("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@._- ") // Or preserve previous keyListener if needed
        }

        // Optionally disable focus change listener when readonly
        if (readOnly) {
            textInputEditText.onFocusChangeListener = null
            textInputEditText.addTextChangedListener(null)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        textStoppedHandler?.removeCallbacksAndMessages(null)
    }
}