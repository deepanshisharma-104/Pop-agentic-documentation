package com.pop.components.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Data formatting extensions for dates, times, and numbers.
 * Supports three formatting styles: Longhand, Standard, and Shorthand.
 */

/**
 * Enum to represent different formatting styles
 */
enum class FormatStyle {
    LONGHAND,
    STANDARD,
    SHORTHAND
}

/**
 * Formats a Date to longhand format: "Wed, 13 Jul 2025"
 */
fun Date.toLonghandDate(): String {
    val format = SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH)
    return format.format(this)
}

/**
 * Formats a Date to standard format: "13 Jul 2025"
 */
fun Date.toStandardDate(): String {
    val format = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    return format.format(this)
}

/**
 * Formats a Date to shorthand format: "13 Jul '25" or "13 Jul" (if current year)
 */
fun Date.toShorthandDate(includeYear: Boolean = true): String {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.time = this
    val dateYear = calendar.get(Calendar.YEAR)

    return if (includeYear && dateYear != currentYear) {
        val format = SimpleDateFormat("dd MMM 'yy", Locale.ENGLISH)
        format.format(this)
    } else {
        val format = SimpleDateFormat("dd MMM", Locale.ENGLISH)
        format.format(this)
    }
}

/**
 * Formats a Date to month and year format: "Nov '25"
 */
fun Date.toMonthYearShorthand(): String {
    val format = SimpleDateFormat("MMM ''yy", Locale.ENGLISH)
    return format.format(this)
}

/**
 * Parses an ISO date string and formats it to month and year format: "Nov '25"
 * Supports formats like "2025-03-18T18:59:17.003216+05:30"
 * Returns null if parsing fails
 */
fun String.toMonthYearShorthand(): String? {
    return try {
        // Try parsing ISO 8601 format with timezone
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        // Remove microseconds and timezone for simpler parsing
        val cleanedDate = this.substringBefore("+").substringBefore("-", this.substringAfter("T").let { 
            if (it.contains("-")) this.substringBefore("T") + "T" + it.substringBefore("-") 
            else this 
        }).let { 
            if (it.length > 19) it.substring(0, 19) else it 
        }
        val date = isoFormat.parse(cleanedDate)
        date?.toMonthYearShorthand()
    } catch (e: Exception) {
        try {
            // Fallback: try simple date format
            val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = simpleFormat.parse(this.substringBefore("T"))
            date?.toMonthYearShorthand()
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Parses ISO 8601 date string to standard format: "dd MMM yyyy"
 * Supports formats: "2026-04-05T18:30:00Z" or "2026-04-05T18:30:00+05:30"
 * Returns null if parsing fails
 */
fun String.parseIso8601ToStandardDate(): String? {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        // Remove timezone suffix (Z or +/-HH:MM)
        val cleanedDate = this.substringBefore("Z")
            .substringBefore("+")
            .let {
                val tIndex = it.indexOf("T")
                if (tIndex > 0) {
                    val datePart = it.substring(0, tIndex + 1)
                    val timePart = it.substring(tIndex + 1).substringBefore("-")
                    datePart + timePart
                } else it
            }
        val date = isoFormat.parse(cleanedDate)
        date?.toStandardDate()
    } catch (e: Exception) {
        null
    }
}

/**
 * Formats a Date based on the specified style
 */
fun Date.formatDate(style: FormatStyle): String {
    return when (style) {
        FormatStyle.LONGHAND -> toLonghandDate()
        FormatStyle.STANDARD -> toStandardDate()
        FormatStyle.SHORTHAND -> toShorthandDate()
    }
}

/**
 * Formats a Long timestamp (milliseconds) to date string
 */
fun Long.toFormattedDate(style: FormatStyle): String {
    return Date(this).formatDate(style)
}

/**
 * Formats a Date to longhand time format: "11:55:55 AM"
 */
fun Date.toLonghandTime(): String {
    val format = SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH)
    return format.format(this)
}

/**
 * Formats a Date to standard time format: "11:55 AM"
 */
fun Date.toStandardTime(): String {
    val format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return format.format(this)
}

/**
 * Formats a Date to shorthand time format: "11:55" or "13:55" (24-hour format)
 */
fun Date.toShorthandTime(): String {
    val format = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    return format.format(this)
}

/**
 * Formats a Date based on the specified style
 */
fun Date.formatTime(style: FormatStyle): String {
    return when (style) {
        FormatStyle.LONGHAND -> toLonghandTime()
        FormatStyle.STANDARD -> toStandardTime()
        FormatStyle.SHORTHAND -> toShorthandTime()
    }
}

/**
 * Formats a Long timestamp (milliseconds) to time string
 */
fun Long.toFormattedTime(style: FormatStyle): String {
    return Date(this).formatTime(style)
}

/**
 * Formats an integer to Indian format: 123456 -> "1,23,456"
 * Indian numbering system: first 3 digits, then every 2 digits
 * Uses the same logic as the existing toIndianFormat() in the codebase
 */
private fun Long.toIndianFormatInteger(): String {
    val numberStr = this.toString()
    if (numberStr.length <= 3) return numberStr

    return numberStr.toCharArray().run {
        reverse()
        val formattedValue = StringBuilder()
        forEachIndexed { index, char ->
            // Add comma at odd indices after index 2
            if (index > 2 && index % 2 != 0 && char != '-') {
                formattedValue.append(',')
            }
            formattedValue.append(char)
        }
        formattedValue.reverse().toString()
    }
}

/**
 * Converts a number to Indian format: 123456 -> "1,23,456"
 * Uses Indian numbering system with commas after thousands and lakhs
 */
fun Number.toIndianFormat(): String {
    val numberStr = this.toDouble().toBigDecimal().toPlainString()
    if (numberStr.contains('.')) {
        val parts = numberStr.split('.')
        val integerPart = try {
            parts[0].toLong()
        } catch (e: NumberFormatException) {
            return numberStr
        }
        val decimalPart = parts[1]
        if (decimalPart.all { it == '0' }) {
            return integerPart.toIndianFormatInteger()
        }
        return "${integerPart.toIndianFormatInteger()}.$decimalPart"
    }
    return try {
        this.toLong().toIndianFormatInteger()
    } catch (e: Exception) {
        numberStr
    }
}

/**
 * Rounds a number and formats it to Indian format
 * Examples: 123456.00 -> "1,23,456", 123456.78 -> "1,23,457"
 */
fun Number.toRoundedIndianFormat(): String {
    val rounded = Math.round(this.toDouble())
    return rounded.toIndianFormat()
}

/**
 * Formats a number to longhand/standard format with Indian numbering system
 * If the number has decimal zeros, they are removed
 * Examples: 123456.00 -> "1,23,456", 123456.78 -> "1,23,457"
 */
fun Number.toLonghandFormat(): String {
    val doubleValue = this.toDouble()
    val rounded = doubleValue.toLong()

    // If the original number equals the rounded number, return without decimals
    if (doubleValue == rounded.toDouble()) {
        return rounded.toIndianFormat()
    }

    // Otherwise, round to nearest integer
    return Math.round(doubleValue).toIndianFormat()
}

/**
 * Formats a number to shorthand format using L (Lakhs) and K (Thousands)
 * Examples:
 * - 1,00,050 -> "1L" (1.0005 lakhs, rounds to 1L)
 * - 1,52,000 -> "1.5L" (1.52 lakhs)
 * - 1,55,650 -> "1.56L" (1.5565 lakhs, rounded to 2 decimals)
 * - 1,000 -> "1K"
 * - 1,520 -> "1.5K" (1.52 thousands)
 * - 1,556 -> "1.6K" (1.556 thousands, rounded to 1 decimal)
 */
fun Number.toShorthandFormat(): String {
    val value = this.toDouble()
    val absValue = Math.abs(value)
    val sign = if (value < 0) "-" else ""

    return when {
        // Format in Lakhs (1,00,000 = 1L)
        absValue >= 100000 -> {
            val lakhs = absValue / 100000
            when {
                // Less than 1.05 lakhs: show as whole number (1L)
                lakhs < 1.05 -> "$sign${lakhs.toInt()}L"
                // Between 1.05L and 10L: show 1-2 decimal places
                lakhs < 10 -> {
                    // Round to 2 decimal places first
                    val roundedTo2Dec = Math.round(lakhs * 100.0) / 100.0
                    // If the second decimal is 0, show only 1 decimal
                    if ((roundedTo2Dec * 10) % 1 == 0.0) {
                        val roundedTo1Dec = Math.round(lakhs * 10.0) / 10.0
                        val df = DecimalFormat("#.#")
                        "$sign${df.format(roundedTo1Dec)}L"
                    } else {
                        val df = DecimalFormat("#.##")
                        "$sign${df.format(roundedTo2Dec)}L"
                    }
                }
                else -> {
                    // 10+ lakhs, round to 1 decimal
                    val rounded = Math.round(lakhs * 10.0) / 10.0
                    val df = DecimalFormat("#.#")
                    "$sign${df.format(rounded)}L"
                }
            }
        }
        // Format in Thousands (1,000 = 1K)
        absValue >= 1000 -> {
            val thousands = absValue / 1000
            when {
                // Exact thousands: 1K, 2K, etc.
                thousands % 1 == 0.0 -> "$sign${thousands.toInt()}K"
                // Less than 10 thousands: show 1 decimal
                thousands < 10 -> {
                    val rounded = Math.round(thousands * 10.0) / 10.0
                    val df = DecimalFormat("#.#")
                    "$sign${df.format(rounded)}K"
                }
                // Between 10K and 100K: keep 1 decimal precision (e.g., 11.6K)
                thousands < 100 -> {
                    val rounded = Math.round(thousands * 10.0) / 10.0
                    val df = DecimalFormat("#.#")
                    "$sign${df.format(rounded)}K"
                }
                else -> {
                    // 100+ thousands, round to nearest
                    val rounded = Math.round(thousands)
                    "$sign${rounded}K"
                }
            }
        }
        // For numbers less than 1000, return rounded integer
        else -> {
            val rounded = Math.round(absValue).toInt()
            "$sign$rounded"
        }
    }
}

/**
 * Formats a number based on the specified style
 */
fun Number.formatNumber(style: FormatStyle): String {
    return when (style) {
        FormatStyle.LONGHAND -> toLonghandFormat()
        FormatStyle.STANDARD -> toLonghandFormat() // Standard uses same as longhand for numbers
        FormatStyle.SHORTHAND -> toShorthandFormat()
    }
}

/**
 * Formats a number with rounding based on style
 * For longhand/standard: rounds to integer and uses Indian format
 * For shorthand: uses L/K notation
 */
fun Number.formatNumberWithRounding(style: FormatStyle): String {
    return when (style) {
        FormatStyle.LONGHAND -> toRoundedIndianFormat()
        FormatStyle.STANDARD -> toRoundedIndianFormat()
        FormatStyle.SHORTHAND -> toShorthandFormat()
    }
}

/**
 * Formats a Date to the specified style
 */
fun Date.format(style: FormatStyle): String = this.formatDate(style)

/**
 * Formats a Long timestamp to date string
 */
fun Long.toDate(style: FormatStyle): String = this.toFormattedDate(style)

/**
 * Formats a Date to time string
 */
fun Date.toTime(style: FormatStyle): String = this.formatTime(style)

/**
 * Formats a Long timestamp to time string
 */
fun Long.toTime(style: FormatStyle): String = this.toFormattedTime(style)

/**
 * Formats a Number to the specified style
 */
fun Number.format(style: FormatStyle): String = this.formatNumber(style)

/**
 * Formats a Number with rounding to the specified style
 */
fun Number.formatWithRounding(style: FormatStyle): String = this.formatNumberWithRounding(style)

// Formats "24-03-2026 14:10" → "24 Mar • 2:10 pm"
fun formatTicketDate(raw: String?): String {
    if (raw.isNullOrBlank()) return ""
    return try {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", java.util.Locale.getDefault())
        val date = sdf.parse(raw) ?: return raw
        val day = SimpleDateFormat("d", java.util.Locale.getDefault()).format(date)
        val mon = SimpleDateFormat("MMM", java.util.Locale.getDefault()).format(date)
        val time = SimpleDateFormat("h:mm a", java.util.Locale.getDefault()).format(date)
            .lowercase()
        "$day $mon • $time"
    } catch (e: Exception) {
        raw
    }
}