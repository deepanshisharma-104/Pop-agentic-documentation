package com.pop.components.ds_components

/**
 * Helper function to handle KeyPadKey presses and return the updated value.
 * This allows PopInputFieldV2 to work with PopKeyPad without direct coupling.
 *
 * @param key The key that was pressed
 * @param currentValue The current input value
 * @param allowDecimal Whether decimal point is allowed (default: true)
 * @param maxLength Optional maximum length for the value (default: null, no limit)
 * @return The updated value after processing the key press
 */
fun handleKeyPadInput(
    key: KeyPadKey,
    currentValue: String,
    allowDecimal: Boolean = true,
    maxLength: Int? = null
): String {
    // Strip formatting characters (commas, spaces) before processing
    // This ensures backspace always deletes digits, not formatting characters
    val cleanValue = currentValue.replace(",", "").replace(" ", "")
    
    android.util.Log.d("AmountInputDebug", "=== handleKeyPadInput ===")
    android.util.Log.d("AmountInputDebug", "Key: ${key.javaClass.simpleName}")
    android.util.Log.d("AmountInputDebug", "currentValue: '$currentValue'")
    android.util.Log.d("AmountInputDebug", "cleanValue: '$cleanValue'")
    
    val result = when (key) {
        is KeyPadKey.Number -> {
            val newValue = cleanValue + key.value
            
            // Check max length (7 characters as per EnterAmountScreen.kt)
            val effectiveMaxLength = maxLength ?: 7
            if (newValue.length > effectiveMaxLength) {
                android.util.Log.d("AmountInputDebug", "Number: maxLength ($effectiveMaxLength) exceeded, returning: '$cleanValue'")
                return@handleKeyPadInput cleanValue
            }
            
            // Prevent first character from being '0' (unless it's "0." for decimal)
            // This matches the validation in EnterAmountScreen.kt line 1501
            if (newValue.isNotEmpty() && newValue.first() == '0' && (newValue.length == 1 || newValue.getOrNull(1) != '.')) {
                android.util.Log.d("AmountInputDebug", "Number: first char cannot be '0' (unless '0.'), returning: '$cleanValue'")
                return@handleKeyPadInput cleanValue
            }
            
            android.util.Log.d("AmountInputDebug", "Number: returning: '$newValue'")
            newValue
        }

        is KeyPadKey.Decimal -> {
            if (allowDecimal && !cleanValue.contains(".")) {
                val result = if (cleanValue.isEmpty()) {
                    "0."
                } else {
                    "$cleanValue."
                }
                android.util.Log.d("AmountInputDebug", "Decimal: returning: '$result'")
                result
            } else {
                android.util.Log.d("AmountInputDebug", "Decimal: not allowed or already exists, returning: '$cleanValue'")
                cleanValue
            }
        }

        is KeyPadKey.Backspace -> {
            if (cleanValue.isNotEmpty()) {
                cleanValue.dropLast(1)
            } else {
                cleanValue
            }
        }
    }
    
    android.util.Log.d("AmountInputDebug", "Result: '$result'")
    android.util.Log.d("AmountInputDebug", "========================")
    
    return result
}

