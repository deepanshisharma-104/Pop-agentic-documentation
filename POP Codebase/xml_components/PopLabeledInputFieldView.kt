package com.pop.components.xml_components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import com.pop.compose_components.R

/**
 * Figma link:
 * https://www.figma.com/design/l1MwQMIjvvJerOUdh4pNpK/Shop-2025?node-id=8917-14407&t=DJzwp2PwBukdeAwc-4
*/
class PopLabeledInputFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : PopInputFieldView(context, attrs, defStyleAttr) {
    var onInputFocusChanged: ((hasFocus: Boolean) -> Unit)? = null

    init {
        // Enable floating label animation
        textInputLayout.isHintAnimationEnabled = true

        textInputLayout.setPadding(0, 0, 0, 0)
        textInputLayout.isExpandedHintEnabled = true
        // Override the parent's container padding completely
        post {
            inputContainer.setPadding(20, 12, 20, 12) // Consistent vertical padding
            inputContainer.gravity = Gravity.CENTER_VERTICAL
            inputContainer.requestLayout()
        }

        val figtreeTypeface = ResourcesCompat.getFont(context, R.font.figtree_medium)
        textInputEditText.typeface = figtreeTypeface
        
        // Override hint colors to be grey for floating labels - use single color to prevent focus changes
        val greyColor = ContextCompat.getColor(context, R.color.text_secondary)
        val greyColorStateList = ColorStateList.valueOf(greyColor)
        textInputLayout.hintTextColor = greyColorStateList
        textInputLayout.defaultHintTextColor = greyColorStateList

        // Read attributes and set up floating label
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FlashInputFieldView,
            0, 0
        ).apply {
            try {
                val labelText = getString(R.styleable.FlashInputFieldView_flashInputLabel)
                val placeholderText = getString(R.styleable.FlashInputFieldView_flashInputPlaceholder)
                
                if (!labelText.isNullOrEmpty()) {
                    // Set the label as hint for floating behavior
                    textInputLayout.hint = labelText
                    // Clear the inner EditText hint to avoid duplication with floating label
                    textInputEditText.hint = null
                } else if (!placeholderText.isNullOrEmpty()) {
                    // If no label but there's placeholder, set it on EditText
                    textInputEditText.hint = placeholderText
                    textInputLayout.hint = null
                }
            } finally {
                recycle()
            }
        }
        
        // Add listeners to dynamically update padding based on floating state
        textInputEditText.setOnFocusChangeListener { _, hasFocus ->

            // Also update container state for border changes
            updateContainerState(inputContainer)
            onInputFocusChanged?.invoke(hasFocus)
        }
        
        // Add text watcher to track text changes for padding updates
        textInputEditText.addTextChangedListener {

        }
     }
     
     override fun onAttachedToWindow() {
         super.onAttachedToWindow()
         inputContainer.setPadding(20, 12, 20, 12) // Consistent
         inputContainer.requestLayout()
     }
     
     // Override setPadding to intercept any padding changes and force our values
     override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
         super.setPadding(left, top, right, bottom)
         try {
             inputContainer.setPadding(20, 12, 20, 12) // Consistent
             inputContainer.requestLayout()
         } catch (e: Exception) {
             // inputContainer might not be initialized yet
         }
     }
} 