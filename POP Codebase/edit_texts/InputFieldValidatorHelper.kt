package com.pop.components.edit_texts

import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import com.pop.components.xml_components.PopInputFieldView

object InputFieldValidatorHelper {

    private fun hasEmptyText(text: String): Boolean {
        return text.isEmpty()
    }

    fun applyValidation(
        field: PopInputFieldView,
        type: InputValidationType,
        onValidationChanged: ((Boolean) -> Unit)? = null
    ) {
        try {
            type.maxLength?.let { field.setEditTextMaxLength(it) }

            when (type) {
                is InputValidationType.NormalInput -> {
                    field.setEditTextMaxLength(type.maxChars)

                    // Build filters array
                    val filters = mutableListOf<InputFilter>(
                        InputFilter.LengthFilter(type.maxChars)
                    )

                    // Apply keyboard type and add corresponding filters
                    when (type.charType) {
                        InputCharType.CAPS_ALL -> {
                            field.textInputEditText.inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                            filters.add(InputFilter.AllCaps())
                        }

                        InputCharType.SMALL_CASE -> {
                            field.textInputEditText.inputType = InputType.TYPE_CLASS_TEXT
                            filters.add(InputFilter { source, _, _, _, _, _ ->
                                source?.toString()?.lowercase()
                            })
                        }

                        InputCharType.NUMERIC -> {
                            field.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
                        }

                        InputCharType.NORMAL -> {
                            field.textInputEditText.inputType = InputType.TYPE_CLASS_TEXT
                        }
                    }

                    // 👉 APPLY REGEX RESTRICTION (use regexFilter)
                    type.regexFilter?.let { regex ->
                        filters.add(CustomRegexInputFilter(regex))
                    }

                    // Apply all filters at once
                    field.textInputEditText.filters = filters.toTypedArray()

                    // Validation logic with callback
                    field.setOnTextChangedListener { text ->
                        if (hasEmptyText(text)) {
                            onValidationChanged?.invoke(false)
                            return@setOnTextChangedListener
                        }

                        // Check if text matches regex (if provided)
                        val matchesRegex = type.regexFilter?.matches(text) ?: true

                        val isValidLength = text.length in type.minLength..type.maxChars
                        val isValid = isValidLength && matchesRegex

                        when {
                            !matchesRegex && text.isNotEmpty() -> {
                                field.errorMessage = "Invalid input format"
                            }

                            !isValidLength && text.isNotEmpty() -> {
                                field.errorMessage =
                                    "Input must be between ${type.minLength} and ${type.maxChars} characters"
                            }

                            else -> {
                                field.isError = false
                                field.errorMessage = null
                            }
                        }

                        // 👉 ALWAYS invoke callback
                        onValidationChanged?.invoke(isValid)
                    }
                }

                is InputValidationType.RegexValidation -> {
                    field.setOnTextChangedListener { text ->
                        if (hasEmptyText(text)) {
                            onValidationChanged?.invoke(false)
                            return@setOnTextChangedListener
                        }
                        if (text.isEmpty()) {
                            field.isError = false
                            field.errorMessage = null
                            onValidationChanged?.invoke(false)
                            return@setOnTextChangedListener
                        }

                        val isValid = type.pattern.matches(text)
                        if (!isValid) {
                            field.errorMessage = type.errorMsg
                        } else {
                            field.isError = false
                            field.errorMessage = null
                        }
                        onValidationChanged?.invoke(isValid && (type.max == null || text.length == type.max))
                    }
                }

                InputValidationType.PAN -> {

                    // Ensure first key press is uppercase
                    field.textInputEditText.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                    field.textInputEditText.filters = arrayOf(
                        InputFilter.LengthFilter(10),
                        InputFilter.AllCaps(), // ensures input is always uppercase
                        PanInputFilter()
                    )

                    field.setOnTextChangedListener { text ->
                        if (hasEmptyText(text)) {
                            onValidationChanged?.invoke(false)
                            return@setOnTextChangedListener
                        }
                        val length = text.length

                        when {
                            length < 5 -> field.textInputEditText.inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS

                            length in 5..8 -> field.textInputEditText.inputType =
                                InputType.TYPE_CLASS_NUMBER

                            length >= 9 -> field.textInputEditText.inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                        }

                        val isValid =
                            (length == 10 && InputValidationType.PAN.regex!!.matches(text))
                        if (!isValid && length == 10) {
                            field.errorMessage = "Invalid PAN format"
                        } else {
                            field.isError = false
                            field.errorMessage = null
                        }
                        onValidationChanged?.invoke(isValid)
                    }
                }

                InputValidationType.Mobile -> {
                    field.textInputEditText.inputType = InputType.TYPE_CLASS_PHONE
                    field.setOnTextChangedListener { text ->
                        if (hasEmptyText(text)) {
                            onValidationChanged?.invoke(false)
                            return@setOnTextChangedListener
                        }
                        val isValid =
                            (text.length == 10 && InputValidationType.Mobile.regex!!.matches(text))

                        if (!isValid && text.length == 10) {
                            field.errorMessage = "Invalid mobile number"
                        } else {
                            field.isError = false
                            field.errorMessage = null
                        }
                        onValidationChanged?.invoke(isValid)
                    }
                }

                InputValidationType.PinCode -> {
                    field.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
                    field.textInputEditText.filters = arrayOf(
                        InputFilter.LengthFilter(6),
                        RegexInputFilter("^[1-9][0-9]{0,5}$".toRegex())
                    )
                    field.setOnTextChangedListener { text ->
                        if (hasEmptyText(text)) {
                            onValidationChanged?.invoke(false)
                            return@setOnTextChangedListener
                        }
                        val isValid =
                            (text.length == 6 && InputValidationType.PinCode.regex!!.matches(text))
                        if (!isValid && text.length == 6) {
                            field.errorMessage = "Invalid pincode"
                        } else {
                            field.isError = false
                            field.errorMessage = null
                        }
                        onValidationChanged?.invoke(isValid)
                    }
                }

                InputValidationType.Email -> {
                    field.setOnTextChangedListener { text ->
                        if (hasEmptyText(text)) {
                            onValidationChanged?.invoke(false)
                            return@setOnTextChangedListener
                        }
                        val isValid =
                            (text.length <= 50 && InputValidationType.Email.regex?.matches(text) == true)
                        if (!isValid) {
                            field.errorMessage = "Enter valid email ID"
                        } else if (text.length == 50) {
                            field.errorMessage = "Email ID cannot exceed 50 characters"
                        } else {
                            field.isError = false
                            field.errorMessage = null
                        }
                        onValidationChanged?.invoke(isValid)
                    }
                }

            }
        } catch (e: Exception) {

        }
    }
}

enum class InputCharType {
    NORMAL,      // default keyboard
    CAPS_ALL,     // only uppercase letters
    SMALL_CASE,   // only lowercase letters
    NUMERIC      // only digits
}

class CustomRegexInputFilter(private val regex: Regex) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source.isNullOrEmpty()) return null // Allow backspace/deletion

        // Build what the resulting text would be after this change
        val newText = buildString {
            append(dest?.substring(0, dstart) ?: "")
            append(source.substring(start, end))
            append(dest?.substring(dend) ?: "")
        }

        // Check if the new text matches the regex pattern
        return if (newText.matches(regex)) {
            null // Accept the input
        } else {
            "" // Reject the input
        }
    }
}

