package com.pop.components.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Test utilities for PopTypography system.
 * 
 * These utilities help validate and test typography configurations,
 * ensuring consistency and correctness across the design system.
 */
object PopTypographyTestUtils {
    
    /**
     * Result of a typography validation check.
     */
    sealed class ValidationResult {
        /**
         * Validation passed successfully.
         */
        object Success : ValidationResult()
        
        /**
         * Validation failed with specific issues.
         * 
         * @param issues List of validation issues found
         */
        data class Failure(val issues: List<String>) : ValidationResult()
    }
    
    /**
     * Validates that a [FontFamilyStyles] object has all required styles
     * and that they match expected specifications.
     * 
     * @param styles The font family styles to validate
     * @param expectedSpecs Optional map of expected specifications (defaults to TypographySpecs)
     * @return ValidationResult indicating success or failure with issues
     */
    fun validateFontFamilyStyles(
        styles: PopTypography.FontFamilyStyles,
        expectedSpecs: Map<TextStyleName, TypographySpecs.StyleSpec> = getDefaultExpectedSpecs()
    ): ValidationResult {
        val issues = mutableListOf<String>()
        
        // Validate all styles exist and match specs
        expectedSpecs.forEach { (styleName, expectedSpec) ->
            val actualStyle = styles.getStyle(styleName)
            val issuesForStyle = validateStyle(actualStyle, expectedSpec, styleName)
            issues.addAll(issuesForStyle)
        }
        
        return if (issues.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Failure(issues)
        }
    }
    
    /**
     * Validates a single [TextStyle] against an expected [TypographySpecs.StyleSpec].
     * 
     * @param style The actual text style
     * @param expectedSpec The expected specification
     * @param styleName The name of the style for error reporting
     * @return List of validation issues (empty if valid)
     */
    fun validateStyle(
        style: TextStyle,
        expectedSpec: TypographySpecs.StyleSpec,
        styleName: TextStyleName
    ): List<String> {
        val issues = mutableListOf<String>()
        val styleNameStr = styleName::class.simpleName ?: "Unknown"
        
        // Check font size
        if (style.fontSize != expectedSpec.fontSize.sp) {
            issues.add("$styleNameStr: Font size mismatch. Expected ${expectedSpec.fontSize}sp, got ${style.fontSize.value}sp")
        }
        
        // Check font weight
        if (style.fontWeight != expectedSpec.fontWeight) {
            issues.add("$styleNameStr: Font weight mismatch. Expected ${expectedSpec.fontWeight}, got ${style.fontWeight}")
        }
        
        // Check line height
        if (style.lineHeight != expectedSpec.lineHeight.sp) {
            issues.add("$styleNameStr: Line height mismatch. Expected ${expectedSpec.lineHeight}sp, got ${style.lineHeight.value}sp")
        }
        
        // Check letter spacing
        if (style.letterSpacing != expectedSpec.letterSpacing.sp) {
            issues.add("$styleNameStr: Letter spacing mismatch. Expected ${expectedSpec.letterSpacing}sp, got ${style.letterSpacing.value}sp")
        }
        
        // Check font family is set
        if (style.fontFamily == null) {
            issues.add("$styleNameStr: Font family is null")
        }
        
        return issues
    }
    
    /**
     * Gets the default expected specifications from TypographySpecs.
     * 
     * @return Map of style names to their expected specifications
     */
    fun getDefaultExpectedSpecs(): Map<TextStyleName, TypographySpecs.StyleSpec> {
        return mapOf(
            TextStyleName.DisplayLarge to TypographySpecs.DisplayLarge,
            TextStyleName.HeadingLarge to TypographySpecs.HeadingLarge,
            TextStyleName.HeadingMedium to TypographySpecs.HeadingMedium,
            TextStyleName.HeadingSmall to TypographySpecs.HeadingSmall,
            TextStyleName.LabelXLarge to TypographySpecs.LabelXLarge,
            TextStyleName.LabelLarge to TypographySpecs.LabelLarge,
            TextStyleName.LabelMedium to TypographySpecs.LabelMedium,
            TextStyleName.LabelSmall to TypographySpecs.LabelSmall,
            TextStyleName.LabelXSmall to TypographySpecs.LabelXSmall,
            TextStyleName.ParagraphLarge to TypographySpecs.ParagraphLarge,
            TextStyleName.ParagraphMedium to TypographySpecs.ParagraphMedium,
            TextStyleName.ParagraphSmall to TypographySpecs.ParagraphSmall,
            TextStyleName.ParagraphXSmall to TypographySpecs.ParagraphXSmall,
            TextStyleName.LinkLarge to TypographySpecs.LinkLarge,
            TextStyleName.LinkMedium to TypographySpecs.LinkMedium
        )
    }
    
    /**
     * Compares two [FontFamilyStyles] objects to check for consistency.
     * 
     * Useful for ensuring different font families maintain consistent
     * style specifications (size, weight, line height) even if fonts differ.
     * 
     * @param styles1 First font family styles
     * @param styles2 Second font family styles
     * @param compareFontFamily Whether to compare font families (default: false)
     * @return List of differences found
     */
    fun compareFontFamilyStyles(
        styles1: PopTypography.FontFamilyStyles,
        styles2: PopTypography.FontFamilyStyles,
        compareFontFamily: Boolean = false
    ): List<String> {
        val differences = mutableListOf<String>()
        val allStyleNames = listOf(
            TextStyleName.DisplayLarge,
            TextStyleName.HeadingLarge,
            TextStyleName.HeadingMedium,
            TextStyleName.HeadingSmall,
            TextStyleName.LabelXLarge,
            TextStyleName.LabelLarge,
            TextStyleName.LabelMedium,
            TextStyleName.LabelSmall,
            TextStyleName.LabelXSmall,
            TextStyleName.ParagraphLarge,
            TextStyleName.ParagraphMedium,
            TextStyleName.ParagraphSmall,
            TextStyleName.ParagraphXSmall,
            TextStyleName.LinkLarge,
            TextStyleName.LinkMedium
        )
        
        allStyleNames.forEach { styleName ->
            val style1 = styles1.getStyle(styleName)
            val style2 = styles2.getStyle(styleName)
            val styleNameStr = styleName::class.simpleName ?: "Unknown"
            
            // Compare font size
            if (style1.fontSize != style2.fontSize) {
                differences.add("$styleNameStr: Font size differs (${style1.fontSize.value}sp vs ${style2.fontSize.value}sp)")
            }
            
            // Compare font weight
            if (style1.fontWeight != style2.fontWeight) {
                differences.add("$styleNameStr: Font weight differs (${style1.fontWeight} vs ${style2.fontWeight})")
            }
            
            // Compare line height
            if (style1.lineHeight != style2.lineHeight) {
                differences.add("$styleNameStr: Line height differs (${style1.lineHeight.value}sp vs ${style2.lineHeight.value}sp)")
            }
            
            // Compare letter spacing
            if (style1.letterSpacing != style2.letterSpacing) {
                differences.add("$styleNameStr: Letter spacing differs (${style1.letterSpacing.value}sp vs ${style2.letterSpacing.value}sp)")
            }
            
            // Compare font family if requested
            if (compareFontFamily && style1.fontFamily != style2.fontFamily) {
                differences.add("$styleNameStr: Font family differs")
            }
        }
        
        return differences
    }
    
    /**
     * Generates a report of all typography styles for a given font family.
     * 
     * Useful for documentation and debugging.
     * 
     * @param styles The font family styles to report on
     * @return Formatted string report
     */
    fun generateTypographyReport(styles: PopTypography.FontFamilyStyles): String {
        val report = StringBuilder()
        report.appendLine("Typography Report")
        report.appendLine("=" * 50)
        report.appendLine()
        
        val allStyleNames = listOf(
            TextStyleName.DisplayLarge,
            TextStyleName.HeadingLarge,
            TextStyleName.HeadingMedium,
            TextStyleName.HeadingSmall,
            TextStyleName.LabelXLarge,
            TextStyleName.LabelLarge,
            TextStyleName.LabelMedium,
            TextStyleName.LabelSmall,
            TextStyleName.LabelXSmall,
            TextStyleName.ParagraphLarge,
            TextStyleName.ParagraphMedium,
            TextStyleName.ParagraphSmall,
            TextStyleName.ParagraphXSmall,
            TextStyleName.LinkLarge,
            TextStyleName.LinkMedium
        )
        
        allStyleNames.forEach { styleName ->
            val style = styles.getStyle(styleName)
            val styleNameStr = styleName::class.simpleName ?: "Unknown"
            
            report.appendLine("$styleNameStr:")
            report.appendLine("  Font Size: ${style.fontSize.value}sp")
            report.appendLine("  Font Weight: ${style.fontWeight}")
            report.appendLine("  Line Height: ${style.lineHeight.value}sp")
            report.appendLine("  Letter Spacing: ${style.letterSpacing.value}sp")
            report.appendLine("  Font Family: ${style.fontFamily?.let { "Set" } ?: "Not set"}")
            report.appendLine()
        }
        
        return report.toString()
    }
    
    /**
     * Extension function for String to repeat it n times.
     */
    private operator fun String.times(n: Int): String {
        return repeat(n)
    }
}


