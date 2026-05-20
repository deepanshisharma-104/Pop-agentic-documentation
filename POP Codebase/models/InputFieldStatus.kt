package com.pop.components.models

/**
 * Status type for input field validation/feedback messages.
 *
 * @property Success Success state with green checkmark icon
 * @property Error Error state with red alert hexagon icon
 * @property Info Informational state with tertiary colored info square icon
 * @property Warning Warning state with yellow alert circle icon
 */
enum class InputFieldStatus {
    /** Success state - displays green checkmark icon with success text color */
    Success,

    /** Error state - displays red alert hexagon icon with destructive text color */
    Error,

    /** Info state - displays info square icon with tertiary text color */
    Info,

    /** Warning state - displays yellow alert circle icon with warning text color */
    Warning
}

