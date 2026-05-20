package com.pop.components.edit_texts

sealed class InputValidationType(
    val maxLength: Int? = null,
    val regex: Regex? = null
) {
    data class NormalInput(
        val minLength: Int = 2,
        val maxChars: Int = 17,
        val charType: InputCharType = InputCharType.NORMAL,
        val regexFilter: Regex? = null
    ) : InputValidationType(maxLength = maxChars, regex = regexFilter)

    data class RegexValidation(
        val pattern: Regex,
        val errorMsg: String,
        val max: Int? = null
    ) : InputValidationType(max, pattern)

    // PAN specific with dynamic keyboard switching
    data object PAN : InputValidationType(10, "^[A-Z]{5}[0-9]{4}[A-Z]$".toRegex())
    data object Mobile : InputValidationType(10, "^[6-9][0-9]{9}$".toRegex())
    data object PinCode : InputValidationType(6, "^[1-9][0-9]{5}$".toRegex())
    data object Email : InputValidationType(
        50,
        "^[A-Za-z0-9](?!.*\\.\\.)([A-Za-z0-9+_.-]{0,62}[A-Za-z0-9])?@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
    )
}
