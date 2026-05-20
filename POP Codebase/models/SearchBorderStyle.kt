package com.pop.components.models

/**
 * Border style for Search input field type.
 *
 * @property Subtle Border is almost invisible/transparent in default state, appears on focus
 * @property DefinedThin Border is always visible as a thin gray line, becomes prominent on focus
 */
enum class SearchBorderStyle {
    /** Subtle border - invisible/transparent in default, visible on focus */
    Subtle,

    /** Defined thin border - always visible thin gray border, prominent on focus */
    DefinedThin
}

