package com.pop.components.edit_texts

import android.text.InputFilter
import android.text.Spanned

class RegexInputFilter(private val fullRegex: Regex) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source.isNullOrEmpty()) return null  // Allow backspace

        val newText = (dest?.substring(0, dstart) ?: "") +
                source.substring(start, end) +
                (dest?.substring(dend) ?: "")

        // 🔹 Allow as long as it *starts* matching the full regex
        val partialRegex = Regex("^${fullRegex.pattern}.*$")

        return if (newText.matches(partialRegex)) null else ""
    }
}

class PanInputFilter : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source.isNullOrEmpty()) return null // allow backspace

        val newText = (dest?.substring(0, dstart) ?: "") +
                source.substring(start, end) +
                (dest?.substring(dend) ?: "")

        // Validate each character by position
        for (i in newText.indices) {
            val c = newText[i]
            when (i) {
                in 0..4 -> if (!c.isLetter()) return "" // only letters
                in 5..8 -> if (!c.isDigit()) return ""  // only digits
                9 -> if (!c.isLetter()) return ""       // last char letter
            }
        }
        return null // accept
    }
}


