package com.pop.components.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pop.components.theme.TypographySpecs.HeadingXTall
import com.pop.compose_components.R

/**
 * Typography style specifications based on design system.
 * 
 * These values define the standard text styles used across the app.
 * All specifications are based on Figma design system requirements.
 * 
 * **Usage:**
 * These specs are used internally by [PopTypography] to generate text styles.
 * For custom typography needs, you can reference these specs or create
 * custom [StyleSpec] objects.
 */
object TypographySpecs {
    /**
     * Specification for a text style including all typographic properties.
     * 
     * @param fontSize Font size in sp
     * @param fontWeight Font weight (e.g., Normal, Medium, Bold)
     * @param lineHeight Line height in sp
     * @param letterSpacing Letter spacing in sp (default: 0)
     */
    data class StyleSpec(
        val fontSize: Int,
        val fontWeight: FontWeight,
        val lineHeight: Int,
        val letterSpacing: Float = 0f
    )
    
    // ============================================================================
    // Display Styles
    // ============================================================================
    
    /**
     * Display Large text style specification.
     * 
     * **Specifications:**
     * - Font Size: 44sp
     * - Font Weight: Bold (700)
     * - Line Height: 52sp
     * - Letter Spacing: -2sp
     * 
     * **Usage:**
     * - Large hero text
     * - Main page titles
     * - Prominent display text
     * 
     * Based on Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4354-21420
     */
    val DisplayLarge = StyleSpec(
        fontSize = 44,
        fontWeight = FontWeight.Bold,
        lineHeight = 52,
        letterSpacing = -2f
    )

    val DisplayMedium = StyleSpec(
        fontSize = 36,
        fontWeight = FontWeight.Bold,
        lineHeight = 40,
        letterSpacing = -2f
    )
    
    // ============================================================================
    // Heading Styles
    // ============================================================================
    
    /**
     * Heading Large text style specification.
     * 
     * **Specifications:**
     * - Font Size: 20sp
     * - Font Weight: SemiBold (600)
     * - Line Height: 26sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Main section headings
     * - Page titles
     * - Primary headings
     */
    val HeadingLarge = StyleSpec(
        fontSize = 20,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 26
    )

    /**
     * Heading Large text style specification.
     *
     * **Specifications:**
     * - Font Size: 30sp
     * - Font Weight: Normal (400)
     * - Line Height: 26sp
     * - Letter Spacing: 0sp
     *
     * **Usage:**
     * - Main section headings
     * - Page titles
     * - Primary headings
     */
    val HeadingXTall = StyleSpec(
        fontSize = 30,
        fontWeight = FontWeight.Normal,
        lineHeight = 40
    )
    
    /**
     * Heading Medium text style specification.
     * 
     * **Specifications:**
     * - Font Size: 18sp
     * - Font Weight: Medium (500)
     * - Line Height: 24sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Subsection headings
     * - Secondary headings
     * - Card titles
     */
    val HeadingMedium = StyleSpec(
        fontSize = 18,
        fontWeight = FontWeight.Medium,
        lineHeight = 24
    )
    
    /**
     * Heading Small text style specification.
     * 
     * **Specifications:**
     * - Font Size: 16sp
     * - Font Weight: Medium (500)
     * - Line Height: 24sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Minor headings
     * - Tertiary headings
     * - Small section titles
     */
    val HeadingSmall = StyleSpec(
        fontSize = 16,
        fontWeight = FontWeight.Medium,
        lineHeight = 24
    )
    
    // ============================================================================
    // Label Styles
    // ============================================================================
    
    /**
     * Label XLarge text style specification.
     * 
     * **Specifications:**
     * - Font Size: 18sp
     * - Font Weight: Medium (500)
     * - Line Height: 26sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Large labels
     * - Prominent form labels
     * - Important captions
     */
    val LabelXLarge = StyleSpec(
        fontSize = 18,
        fontWeight = FontWeight.Medium,
        lineHeight = 26
    )
    
    /**
     * Label Large text style specification.
     * 
     * **Specifications:**
     * - Font Size: 16sp
     * - Font Weight: Bold (700)
     * - Line Height: 24sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Prominent labels
     * - Important form labels
     * - Key captions
     */
    val LabelLarge = StyleSpec(
        fontSize = 16,
        fontWeight = FontWeight.Bold,
        lineHeight = 24
    )
    
    /**
     * Label Medium text style specification.
     * 
     * **Specifications:**
     * - Font Size: 16sp
     * - Font Weight: Medium (500)
     * - Line Height: 24sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Standard labels
     * - Form labels
     * - Default captions
     */
    val LabelMedium = StyleSpec(
        fontSize = 16,
        fontWeight = FontWeight.Medium,
        lineHeight = 24
    )
    
    /**
     * Label Small text style specification.
     * 
     * **Specifications:**
     * - Font Size: 14sp
     * - Font Weight: Medium (500)
     * - Line Height: 20sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Small labels
     * - Secondary captions
     * - Helper text
     */
    val LabelSmall = StyleSpec(
        fontSize = 14,
        fontWeight = FontWeight.Medium,
        lineHeight = 20
    )
    
    /**
     * Label XSmall text style specification.
     * 
     * **Specifications:**
     * - Font Size: 12sp
     * - Font Weight: Medium (500)
     * - Line Height: 16sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Extra small labels
     * - Fine print
     * - Tiny captions
     */
    val LabelXSmall = StyleSpec(
        fontSize = 12,
        fontWeight = FontWeight.Medium,
        lineHeight = 16
    )

    val LabelXXSmall = StyleSpec(
        fontSize = 10,
        fontWeight = FontWeight.Medium,
        lineHeight = 12
    )
    
    // ============================================================================
    // Paragraph Styles
    // ============================================================================
    
    /**
     * Paragraph Large text style specification.
     * 
     * **Specifications:**
     * - Font Size: 24sp
     * - Font Weight: Bold (700)
     * - Line Height: 32sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Large body text
     * - Prominent paragraphs
     * - Important content
     */
    val ParagraphLarge = StyleSpec(
        fontSize = 24,
        fontWeight = FontWeight.Bold,
        lineHeight = 32
    )
    
    /**
     * Paragraph Medium text style specification.
     * 
     * **Specifications:**
     * - Font Size: 16sp
     * - Font Weight: Medium (500)
     * - Line Height: 24sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Standard body text
     * - Default paragraphs
     * - Main content
     */
    val ParagraphMedium = StyleSpec(
        fontSize = 16,
        fontWeight = FontWeight.Medium,
        lineHeight = 24
    )
    
    /**
     * Paragraph Small text style specification.
     * 
     * **Specifications:**
     * - Font Size: 14sp
     * - Font Weight: Medium (500)
     * - Line Height: 22sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Small body text
     * - Secondary content
     * - Compact paragraphs
     */
    val ParagraphSmall = StyleSpec(
        fontSize = 14,
        fontWeight = FontWeight.Medium,
        lineHeight = 22
    )
    
    /**
     * Paragraph XSmall text style specification.
     * 
     * **Specifications:**
     * - Font Size: 12sp
     * - Font Weight: Normal (400)
     * - Line Height: 20sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Extra small body text
     * - Fine print
     * - Compact content
     */
    val ParagraphXSmall = StyleSpec(
        fontSize = 12,
        fontWeight = FontWeight.Normal,
        lineHeight = 20
    )
    
    // ============================================================================
    // Link Styles
    // ============================================================================
    
    /**
     * Link Large text style specification.
     * 
     * **Specifications:**
     * - Font Size: 14sp
     * - Font Weight: SemiBold (600)
     * - Line Height: 20sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Large links
     * - Prominent navigation
     * - Important clickable text
     * 
     * **Note:** Underline decoration should be applied separately in Compose
     */
    val LinkLarge = StyleSpec(
        fontSize = 14,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20
    )
    
    /**
     * Link Medium text style specification.
     * 
     * **Specifications:**
     * - Font Size: 12sp
     * - Font Weight: Medium (500)
     * - Line Height: 16sp
     * - Letter Spacing: 0sp
     * 
     * **Usage:**
     * - Standard links
     * - Default navigation
     * - Regular clickable text
     * 
     * **Note:** Underline decoration should be applied separately in Compose
     */
    val LinkMedium = StyleSpec(
        fontSize = 12,
        fontWeight = FontWeight.Medium,
        lineHeight = 16
    )
}

/**
 * Sealed class representing all available text style names.
 * 
 * This provides type-safe access to text styles and prevents typos.
 * Use with [PopTypography.getStyle] for type-safe style retrieval.
 */
sealed class TextStyleName {
    // Display Styles
    object DisplayLarge : TextStyleName()
    
    // Heading Styles
    object HeadingLarge : TextStyleName()
    object HeadingMedium : TextStyleName()
    object HeadingSmall : TextStyleName()
    
    // Label Styles
    object LabelXLarge : TextStyleName()
    object LabelLarge : TextStyleName()
    object LabelMedium : TextStyleName()
    object LabelSmall : TextStyleName()
    object LabelXSmall : TextStyleName()
    object LabelXXSmall : TextStyleName()

    // Paragraph Styles
    object ParagraphLarge : TextStyleName()
    object ParagraphMedium : TextStyleName()
    object ParagraphSmall : TextStyleName()
    object ParagraphXSmall : TextStyleName()
    
    // Link Styles
    object LinkLarge : TextStyleName()
    object LinkMedium : TextStyleName()
}

/**
 * Configuration for customizing text styles per font family.
 * 
 * Allows overriding specific style specifications for a font family.
 * If a style is null, the default [TypographySpecs] value will be used.
 * 
 * **Example:**
 * ```
 * val customConfig = FontFamilyStyleConfig(
 *     headingLarge = TypographySpecs.StyleSpec(
 *         fontSize = 22,
 *         fontWeight = FontWeight.Bold,
 *         lineHeight = 28
 *     )
 * )
 * ```
 */
data class FontFamilyStyleConfig(
    val displayLarge: TypographySpecs.StyleSpec? = null,
    val displayMedium: TypographySpecs.StyleSpec? = null,
    val headingXTall: TypographySpecs.StyleSpec? = null,
    val headingLarge: TypographySpecs.StyleSpec? = null,
    val headingMedium: TypographySpecs.StyleSpec? = null,
    val headingSmall: TypographySpecs.StyleSpec? = null,
    val labelXLarge: TypographySpecs.StyleSpec? = null,
    val labelLarge: TypographySpecs.StyleSpec? = null,
    val labelMedium: TypographySpecs.StyleSpec? = null,
    val labelSmall: TypographySpecs.StyleSpec? = null,
    val labelXSmall: TypographySpecs.StyleSpec? = null,
    val labelXXSmall: TypographySpecs.StyleSpec? = null,
    val paragraphLarge: TypographySpecs.StyleSpec? = null,
    val paragraphMedium: TypographySpecs.StyleSpec? = null,
    val paragraphSmall: TypographySpecs.StyleSpec? = null,
    val paragraphXSmall: TypographySpecs.StyleSpec? = null,
    val linkLarge: TypographySpecs.StyleSpec? = null,
    val linkMedium: TypographySpecs.StyleSpec? = null
)

/**
 * Design System Typography based on Figma specifications.
 * 
 * This is a separate typography system from the legacy FlashTypography.
 * 
 * This typography system supports multiple font families. Each font family
 * has its own set of text styles (display, heading, label, paragraph, link).
 * 
 * **Features:**
 * - Type-safe style access via [TextStyleName]
 * - Per-font-family customization via [FontFamilyStyleConfig]
 * - Comprehensive style specifications in [TypographySpecs]
 * - Validation and error handling
 * 
 * **Usage Examples:**
 * 
 * Basic usage with default styles:
 * ```kotlin
 * Text(
 *     text = "Hello",
 *     style = PopTypography.figtreeStyles.headingLarge
 * )
 * ```
 * 
 * Type-safe style access:
 * ```kotlin
 * val style = PopTypography.getStyle(
 *     TextStyleName.HeadingLarge,
 *     PopTypography.figtree
 * )
 * ```
 * 
 * **To add a new font family:**
 * 1. Define the FontFamily (e.g., `val myFont = FontFamily(...)`)
 * 2. Optionally create a [FontFamilyStyleConfig] for customizations
 * 3. Create a FontFamilyStyles object using `createFontFamilyStyles(myFont, config)`
 * 4. Add it as a property (e.g., `val myFontStyles = createFontFamilyStyles(myFont)`)
 */
object PopTypography {
    // ============================================================================
    // Font Family Definitions
    // ============================================================================
    
    /**
     * Figtree font family with standard weights.
     * This is the default font family for the design system.
     */
    val figtree = FontFamily(
        Font(R.font.figtree_regular, FontWeight.Normal),
        Font(R.font.figtree_medium, FontWeight.Medium),
        Font(R.font.figtree_semibold, FontWeight.SemiBold),
        Font(R.font.figtree_bold, FontWeight.Bold)
    )

    /**
     * Awesome Serif Italic font family with regular variants (default).
     * This is the default variant for awesomeSerifItalic.
     * 
     * **Available Weights:**
     * - Light (300)
     * - Normal (400) - includes regular and smallregular
     * - Medium (500)
     * - SemiBold (600) - note: only tall/extratall variants exist
     * - Bold (700)
     * 
     * **Note:** For other variants (tall, extratall), use:
     * - [awesomeSerifItalicTall] for tall variants
     * - [awesomeSerifItalicExtraTall] for extratall variants
     */
    val awesomeSerifItalic = FontFamily(
        // Light variants (300) - regular only
        Font(R.font.awesomeserifitalic_lightregular, FontWeight.Light),
        // Normal variants (400) - regular and smallregular
        Font(R.font.awesomeserifitalic_regular, FontWeight.Normal),
        Font(R.font.awesomeserifitalic_smallregular, FontWeight.Normal),
        // Medium variants (500) - regular only
        Font(R.font.awesomeserifitalic_mediumregular, FontWeight.Medium),
        // SemiBold variants (600) - note: only tall/extratall exist, using tall as default
        Font(R.font.awesomeserifitalic_semiboldtall, FontWeight.SemiBold),
        // Bold variants (700) - regular only
        Font(R.font.awesomeserifitalic_boldregular, FontWeight.Bold)
    )

    /**
     * Awesome Serif Italic font family with tall variants.
     * Use this when you need the "tall" variant of the font.
     * 
     * **Available Weights:**
     * - Light (300)
     * - Normal (400)
     * - Medium (500)
     * - SemiBold (600)
     * - Bold (700)
     */
    val awesomeSerifItalicTall = FontFamily(
        // Light variants (300)
        Font(R.font.awesomeserifitalic_lighttall, FontWeight.Light),
        // Normal variants (400)
        Font(R.font.awesomeserifitalic_tall, FontWeight.Normal),
        // Medium variants (500)
        Font(R.font.awesomeserifitalic_mediumtall, FontWeight.Medium),
        // SemiBold variants (600)
        Font(R.font.awesomeserifitalic_semiboldtall, FontWeight.SemiBold),
        // Bold variants (700)
        Font(R.font.awesomeserifitalic_boldtall, FontWeight.Bold)
    )

    /**
     * Awesome Serif Italic font family with extratall variants.
     * Use this when you need the "extratall" variant of the font.
     * 
     * **Available Weights:**
     * - Light (300)
     * - Normal (400)
     * - Medium (500)
     * - SemiBold (600)
     * - Bold (700)
     */
    val awesomeSerifItalicExtraTall = FontFamily(
        // Light variants (300)
        Font(R.font.awesomeserifitalic_lightextratall, FontWeight.Light),
        // Normal variants (400)
        Font(R.font.awesomeserifitalic_extratall, FontWeight.Normal),
        // Medium variants (500)
        Font(R.font.awesomeserifitalic_mediumextratall, FontWeight.Medium),
        // SemiBold variants (600)
        Font(R.font.awesomeserifitalic_semiboldextratall, FontWeight.SemiBold),
        // Bold variants (700)
        Font(R.font.awesomeserifitalic_boldexratall, FontWeight.Bold)
    )

    // ============================================================================
    // Typography Style Generator
    // ============================================================================
    
    /**
     * Data class containing all text styles for a specific font family.
     * This makes it easy to access styles for different font families.
     */
    data class FontFamilyStyles(
        // Display Styles
        val displayLarge: TextStyle,
        val displayMedium: TextStyle,

        // Heading Styles
        val headingXTall: TextStyle,
        val headingLarge: TextStyle,
        val headingMedium: TextStyle,
        val headingSmall: TextStyle,
        
        // Label Styles
        val labelXLarge: TextStyle,
        val labelLarge: TextStyle,
        val labelMedium: TextStyle,
        val labelSmall: TextStyle,
        val labelXSmall: TextStyle,
        val labelXXSmall: TextStyle,

        // Paragraph Styles
        val paragraphLarge: TextStyle,
        val paragraphMedium: TextStyle,
        val paragraphSmall: TextStyle,
        val paragraphXSmall: TextStyle,
        
        // Link Styles
        val linkLarge: TextStyle,
        val linkMedium: TextStyle
    ) {
        /**
         * Get a text style by name using type-safe [TextStyleName].
         * 
         * @param styleName The type-safe style name
         * @return The corresponding [TextStyle]
         * @throws IllegalArgumentException if style name is not recognized
         */
        fun getStyle(styleName: TextStyleName): TextStyle {
            return when (styleName) {
                is TextStyleName.DisplayLarge -> displayLarge
                is TextStyleName.HeadingLarge -> headingLarge
                is TextStyleName.HeadingMedium -> headingMedium
                is TextStyleName.HeadingSmall -> headingSmall
                is TextStyleName.LabelXLarge -> labelXLarge
                is TextStyleName.LabelLarge -> labelLarge
                is TextStyleName.LabelMedium -> labelMedium
                is TextStyleName.LabelSmall -> labelSmall
                is TextStyleName.LabelXSmall -> labelXSmall
                is TextStyleName.LabelXXSmall -> labelXXSmall
                is TextStyleName.ParagraphLarge -> paragraphLarge
                is TextStyleName.ParagraphMedium -> paragraphMedium
                is TextStyleName.ParagraphSmall -> paragraphSmall
                is TextStyleName.ParagraphXSmall -> paragraphXSmall
                is TextStyleName.LinkLarge -> linkLarge
                is TextStyleName.LinkMedium -> linkMedium
            }
        }
    }
    
    /**
     * Creates a [TextStyle] from a [TypographySpecs.StyleSpec] and [FontFamily].
     * 
     * @param spec The style specification
     * @param fontFamily The font family to use
     * @return A configured [TextStyle]
     */
    private fun createTextStyle(
        spec: TypographySpecs.StyleSpec,
        fontFamily: FontFamily
    ): TextStyle {
        return TextStyle(
            fontFamily = fontFamily,
            fontSize = spec.fontSize.sp,
            fontWeight = spec.fontWeight,
            letterSpacing = spec.letterSpacing.sp,
            lineHeight = spec.lineHeight.sp
        )
    }
    
    /**
     * Validates that a font family has the required font weights available.
     * 
     * This is a best-effort validation. Since FontFamily doesn't expose
     * its internal font list, we log warnings for potential issues.
     * 
     * @param fontFamily The font family to validate
     * @param fontFamilyName The name of the font family for logging
     */
    private fun validateFontFamily(fontFamily: FontFamily, fontFamilyName: String) {
        // Note: FontFamily doesn't expose its fonts directly, so we can't
        // fully validate. In a production system, you might want to maintain
        // a registry of available font weights per family.
        // For now, we rely on compile-time resource checking.
        
        // Future enhancement: Maintain a registry of font weights per family
        // and validate against that registry.
    }
    
    /**
     * Creates a complete set of text styles for a given font family.
     * 
     * This function generates all standard text styles (display, heading, label, paragraph, link)
     * using the specified font family and optional customizations.
     * 
     * **Note:** For Awesome Serif Italic, use the explicit variant style sets:
     * - [awesomeSerifItalicTallStyles] for tall variants
     * - [awesomeSerifItalicExtraTallStyles] for extratall variants
     * - Or call this function with [awesomeSerifItalic] for regular variants
     * 
     * @param fontFamily The FontFamily to use for all text styles
     * @param config Optional configuration for customizing specific styles per font family
     * @return FontFamilyStyles containing all text styles for the font family
     */
    fun createFontFamilyStyles(
        fontFamily: FontFamily,
        config: FontFamilyStyleConfig = FontFamilyStyleConfig()
    ): FontFamilyStyles {
        // Validate font family
        validateFontFamily(fontFamily, "FontFamily")
        
        // Helper function to get spec (custom or default)
        fun getSpec(
            custom: TypographySpecs.StyleSpec?,
            default: TypographySpecs.StyleSpec
        ): TypographySpecs.StyleSpec = custom ?: default

        return FontFamilyStyles(
    // Display Styles
            displayLarge = createTextStyle(
                getSpec(config.displayLarge, TypographySpecs.DisplayLarge),
                fontFamily
            ),
            displayMedium = createTextStyle(
                getSpec(config.displayMedium, TypographySpecs.DisplayLarge),
                fontFamily
            ),
    // Heading Styles
            headingXTall = createTextStyle(
                getSpec(config.headingXTall, HeadingXTall), fontFamily),
            headingLarge = createTextStyle(
                getSpec(config.headingLarge, TypographySpecs.HeadingLarge),
                fontFamily
            ),
            headingMedium = createTextStyle(
                getSpec(config.headingMedium, TypographySpecs.HeadingMedium),
                fontFamily
            ),
            headingSmall = createTextStyle(
                getSpec(config.headingSmall, TypographySpecs.HeadingSmall),
                fontFamily
            ),

    // Label Styles
            labelXLarge = createTextStyle(
                getSpec(config.labelXLarge, TypographySpecs.LabelXLarge),
                fontFamily
            ),
            labelLarge = createTextStyle(
                getSpec(config.labelLarge, TypographySpecs.LabelLarge),
                fontFamily
            ),
            labelMedium = createTextStyle(
                getSpec(config.labelMedium, TypographySpecs.LabelMedium),
                fontFamily
            ),
            labelSmall = createTextStyle(
                getSpec(config.labelSmall, TypographySpecs.LabelSmall),
                fontFamily
            ),
            labelXSmall = createTextStyle(
                getSpec(config.labelXSmall, TypographySpecs.LabelXSmall),
                fontFamily
            ),
            labelXXSmall = createTextStyle(
                getSpec(config.labelXXSmall, TypographySpecs.LabelXXSmall),
                fontFamily
            ),

    // Paragraph Styles
            paragraphLarge = createTextStyle(
                getSpec(config.paragraphLarge, TypographySpecs.ParagraphLarge),
                fontFamily
            ),
            paragraphMedium = createTextStyle(
                getSpec(config.paragraphMedium, TypographySpecs.ParagraphMedium),
                fontFamily
            ),
            paragraphSmall = createTextStyle(
                getSpec(config.paragraphSmall, TypographySpecs.ParagraphSmall),
                fontFamily
            ),
            paragraphXSmall = createTextStyle(
                getSpec(config.paragraphXSmall, TypographySpecs.ParagraphXSmall),
                fontFamily
            ),

    // Link Styles
            linkLarge = createTextStyle(
                getSpec(config.linkLarge, TypographySpecs.LinkLarge),
                fontFamily
            ),
            linkMedium = createTextStyle(
                getSpec(config.linkMedium, TypographySpecs.LinkMedium),
                fontFamily
            )
        )
    }

    // ============================================================================
    // Font Family Style Sets
    // ============================================================================
    
    /**
     * Text styles using the Figtree font family.
     * This is the default font family for the design system.
     */
    val figtreeStyles: FontFamilyStyles = createFontFamilyStyles(figtree)
    
    /**
     * Text styles using the Awesome Serif Italic font family (tall variants).
     * Use this when you need the "tall" variant of the font.
     */
    val awesomeSerifItalicTallStyles: FontFamilyStyles = createFontFamilyStyles(awesomeSerifItalicTall)

    /**
     * Text styles using the Awesome Serif Italic font family (extratall variants).
     * Use this when you need the "extratall" variant of the font.
     */
    val awesomeSerifItalicExtraTallStyles: FontFamilyStyles = createFontFamilyStyles(awesomeSerifItalicExtraTall)

    // ============================================================================
    // Type-Safe Style Access
    // ============================================================================
    
    /**
     * Get a text style by name and font family using type-safe access.
     * 
     * **Example:**
     * ```kotlin
     * val style = PopTypography.getStyle(
     *     TextStyleName.HeadingLarge,
     *     PopTypography.figtree
     * )
     * ```
     * 
     * @param styleName The type-safe style name
     * @param fontFamily The font family to use
     * @return The corresponding [TextStyle]
     */
    fun getStyle(styleName: TextStyleName, fontFamily: FontFamily): TextStyle {
        val styles = when (fontFamily) {
            figtree -> figtreeStyles
            awesomeSerifItalicTall -> awesomeSerifItalicTallStyles
            awesomeSerifItalicExtraTall -> awesomeSerifItalicExtraTallStyles
            else -> {
                // For custom font families (including awesomeSerifItalic regular variants),
                // create styles on-the-fly
                // In production, you might want to cache these
                createFontFamilyStyles(fontFamily)
            }
        }
        return styles.getStyle(styleName)
    }
    
    /**
     * Get a text style by name using the default Figtree font family.
     * 
     * **Example:**
     * ```kotlin
     * val style = PopTypography.getStyle(TextStyleName.HeadingLarge)
     * ```
     * 
     * @param styleName The type-safe style name
     * @return The corresponding [TextStyle] using Figtree
     */
    fun getStyle(styleName: TextStyleName): TextStyle {
        return figtreeStyles.getStyle(styleName)
    }

    // ============================================================================
    // Backward Compatibility - Default Styles (using Figtree)
    // ============================================================================
    
    /**
     * Default text styles using Figtree font family.
     * 
     * @deprecated These properties are deprecated. Use one of the following approaches instead:
     * 
     * **Recommended approaches:**
     * 
     * 1. **Explicit font family access (Recommended for most cases):**
     *    ```kotlin
     *    // Old: PopTypography.headingLarge
     *    // New: PopTypography.figtreeStyles.headingLarge
     *    Text(
     *        text = "Title",
     *        style = PopTypography.figtreeStyles.headingLarge
     *    )
     *    ```
     * 
     * 2. **Type-safe access (Recommended for dynamic style selection):**
     *    ```kotlin
     *    // Old: PopTypography.headingLarge
     *    // New: PopTypography.getStyle(TextStyleName.HeadingLarge)
     *    Text(
     *        text = "Title",
     *        style = PopTypography.getStyle(TextStyleName.HeadingLarge)
     *    )
     *    ```
     * 
     * 3. **For other font families:**
     *    ```kotlin
     *    // Awesome Serif Italic (tall variant)
     *    PopTypography.awesomeSerifItalicTallStyles.headingLarge
     *    
     *    // Awesome Serif Italic (extratall variant)
     *    PopTypography.awesomeSerifItalicExtraTallStyles.headingLarge
     *    
     *    // With type-safe access and custom font
     *    PopTypography.getStyle(TextStyleName.HeadingLarge, customFontFamily)
     *    ```
     * 
     * **Why migrate?**
     * - Explicit font family access makes code more maintainable
     * - Type-safe access prevents typos and enables IDE autocomplete
     * - Better support for multiple font families
     * - Clearer intent in code
     */
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.headingLarge or PopTypography.getStyle(TextStyleName.HeadingLarge) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.displayLarge",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val displayLarge = figtreeStyles.displayLarge
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.headingLarge or PopTypography.getStyle(TextStyleName.HeadingLarge) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.headingLarge",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val headingLarge = figtreeStyles.headingLarge
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.headingMedium or PopTypography.getStyle(TextStyleName.HeadingMedium) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.headingMedium",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val headingMedium = figtreeStyles.headingMedium
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.headingSmall or PopTypography.getStyle(TextStyleName.HeadingSmall) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.headingSmall",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val headingSmall = figtreeStyles.headingSmall
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.labelXLarge or PopTypography.getStyle(TextStyleName.LabelXLarge) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.labelXLarge",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val labelXLarge = figtreeStyles.labelXLarge
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.labelLarge or PopTypography.getStyle(TextStyleName.LabelLarge) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.labelLarge",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val labelLarge = figtreeStyles.labelLarge
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.labelMedium or PopTypography.getStyle(TextStyleName.LabelMedium) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.labelMedium",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val labelMedium = figtreeStyles.labelMedium
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.labelSmall or PopTypography.getStyle(TextStyleName.LabelSmall) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.labelSmall",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val labelSmall = figtreeStyles.labelSmall
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.labelXSmall or PopTypography.getStyle(TextStyleName.LabelXSmall) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.labelXSmall",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val labelXSmall = figtreeStyles.labelXSmall
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.paragraphLarge or PopTypography.getStyle(TextStyleName.ParagraphLarge) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.paragraphLarge",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val paragraphLarge = figtreeStyles.paragraphLarge
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.paragraphMedium or PopTypography.getStyle(TextStyleName.ParagraphMedium) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.paragraphMedium",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val paragraphMedium = figtreeStyles.paragraphMedium
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.paragraphSmall or PopTypography.getStyle(TextStyleName.ParagraphSmall) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.paragraphSmall",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val paragraphSmall = figtreeStyles.paragraphSmall
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.paragraphXSmall or PopTypography.getStyle(TextStyleName.ParagraphXSmall) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.paragraphXSmall",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val paragraphXSmall = figtreeStyles.paragraphXSmall
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.linkLarge or PopTypography.getStyle(TextStyleName.LinkLarge) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.linkLarge",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val linkLarge = figtreeStyles.linkLarge
    
    @Deprecated(
        message = "Use PopTypography.figtreeStyles.linkMedium or PopTypography.getStyle(TextStyleName.LinkMedium) instead. " +
                "See KDoc for migration examples.",
        replaceWith = ReplaceWith(
            expression = "PopTypography.figtreeStyles.linkMedium",
            imports = ["com.pop.components.theme.PopTypography"]
        ),
        level = DeprecationLevel.WARNING
    )
    val linkMedium = figtreeStyles.linkMedium
}

/**
 * CompositionLocal for accessing PopTypography in Compose.
 * 
 * **Usage:**
 * ```kotlin
 * CompositionLocalProvider(LocalPopTypography provides customTypography) {
 *     // Your composable content
 * }
 * ```
 */
val LocalPopTypography = staticCompositionLocalOf { PopTypography }
