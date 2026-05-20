package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.ds_components.PopMarqueeText
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTheme
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor

/**
 * Preview functions for PopTypography demonstrating all font families.
 * 
 * This file contains preview functions to showcase PopMarqueeText with different
 * font families (Figtree and Awesome Serif Italic) and all text styles.
 * 
 * Marquee animation is disabled (shouldAnimate = false) to focus on font family differences.
 */

@Preview(
    name = "PopTypography - All Font Families",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    heightDp = 1200
)
@Composable
fun PopTypographyPreviews() {
    PopTheme {
        PopTypographyPreviewScreen()
    }
}

@Composable
private fun PopTypographyPreviewScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceColor.Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(PopSpacing.Spacing16)
        ) {
            // Title
            Text(
                text = "PopMarqueeText Font Family Demo",
                style = PopTypography.headingLarge,
                color = TextColor.Primary,
                modifier = Modifier.padding(bottom = PopSpacing.Spacing24)
            )
            
            Text(
                text = "Marquee animation is disabled to focus on font family differences",
                style = PopTypography.paragraphSmall,
                color = TextColor.Secondary,
                modifier = Modifier.padding(bottom = PopSpacing.Spacing24)
            )
            
            // Figtree Font Family Section
            FontFamilySection(
                title = "Figtree Font Family",
                fontFamilyStyles = PopTypography.figtreeStyles
            )
            
            Spacer(modifier = Modifier.height(PopSpacing.Spacing32))
            
            // Awesome Serif Italic Font Family Section - Tall Variant
            FontFamilySection(
                title = "Awesome Serif Italic Font Family (Tall Variant)",
                fontFamilyStyles = PopTypography.awesomeSerifItalicTallStyles
            )
            
            Spacer(modifier = Modifier.height(PopSpacing.Spacing32))
            
            // Awesome Serif Italic Font Family Section - Extra Tall Variant
            FontFamilySection(
                title = "Awesome Serif Italic Font Family (Extra Tall Variant)",
                fontFamilyStyles = PopTypography.awesomeSerifItalicExtraTallStyles
            )
        }
    }
}

@Composable
private fun FontFamilySection(
    title: String,
    fontFamilyStyles: PopTypography.FontFamilyStyles
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Section Title
        Text(
            text = title,
            style = PopTypography.headingMedium,
            color = TextColor.Primary,
            modifier = Modifier.padding(bottom = PopSpacing.Spacing16)
        )
        
        // Display Styles
        Text(
            text = "Display Styles",
            style = PopTypography.labelLarge,
            color = TextColor.Secondary,
            modifier = Modifier.padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Display Large - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.displayLarge,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing12)
        )
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        
        // Heading Styles
        Text(
            text = "Heading Styles",
            style = PopTypography.labelLarge,
            color = TextColor.Secondary,
            modifier = Modifier.padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Heading Large - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.headingLarge,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Heading Medium - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.headingMedium,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Heading Small - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.headingSmall,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing12)
        )
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        
        // Label Styles
        Text(
            text = "Label Styles",
            style = PopTypography.labelLarge,
            color = TextColor.Secondary,
            modifier = Modifier.padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Label XLarge - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.labelXLarge,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Label Large - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.labelLarge,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Label Medium - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.labelMedium,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Label Small - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.labelSmall,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Label XSmall - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.labelXSmall,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing12)
        )
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        
        // Paragraph Styles
        Text(
            text = "Paragraph Styles",
            style = PopTypography.labelLarge,
            color = TextColor.Secondary,
            modifier = Modifier.padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Paragraph Large - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.paragraphLarge,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Paragraph Medium - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.paragraphMedium,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Paragraph Small - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.paragraphSmall,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Paragraph XSmall - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.paragraphXSmall,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing12)
        )
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        
        // Link Styles
        Text(
            text = "Link Styles",
            style = PopTypography.labelLarge,
            color = TextColor.Secondary,
            modifier = Modifier.padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Link Large - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.linkLarge,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing8)
        )
        PopMarqueeText(
            text = "Link Medium - The quick brown fox jumps over the lazy dog",
            style = fontFamilyStyles.linkMedium,
            color = TextColor.Primary,
            shouldAnimate = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PopSpacing.Spacing12)
        )
    }
}

