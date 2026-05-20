package com.pop.components.models

/**
 * Type of input field variant.
 *
 * @property UnderlineNakedSmall Naked small underline style (from Figma design)
 * @property UnderlineNakedLarge Naked large underline style (from Figma design)
 * @property MobileInputField Mobile number input field with rounded rectangle and phone icon (from Figma design)
 * @property Basic Standard input field with rounded rectangle, title above, and status messages (from Figma design)
 * @property Search Rounded rectangle input field with search icon on left, optional clear button, and trailing chip support (from Figma design)
 * @property Small Compact input field with 28dp height, 8dp border radius, and 12px text (from Figma design)
 */
enum class PopInputFieldType {
    /** UnderlineNakedSmall style - naked small underline (from Figma design) */
    UnderlineNakedSmall,

    /** UnderlineNakedLarge style - naked large underline (from Figma design) */
    UnderlineNakedLarge,

    /** MobileInputField style - mobile number input with rounded rectangle, country code prefix, and phone icon (from Figma design) */
    MobileInputField,

    /** Basic style - rounded rectangle input field with animated hint text and optional icons */
    Basic,

    /** Search style - rounded rectangle input field with search icon on left, optional clear button, and trailing chip support (from Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4536-12367) */
    Search,

    /** Small style - compact input field with 28dp height, 8dp border radius, and 12px text (from Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=8220-47609) */
    Small,
}

