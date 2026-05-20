package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.PopTitleBar
import com.pop.components.models.PopTitleBarConfig
import com.pop.components.theme.Icons

/**
 * Preview functions for PopTitleBar component
 * 
 * This file contains all preview functions with various permutations
 * and combinations to showcase all features of the PopTitleBar component.
 */

// ============================================================================
// Basic Previews
// ============================================================================

/**
 * Preview: Title only
 */
@Preview(
    name = "Title Only",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarTitleOnly() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title"
        )
    )
}

/**
 * Preview: Title with body
 */
@Preview(
    name = "Title + Body",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarWithBody() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            bodyText = "Body text below title"
        )
    )
}

// ============================================================================
// Icon Variations - Title Icons
// ============================================================================

/**
 * Preview: Title with left icon
 */
@Preview(
    name = "Title + Left Icon",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarTitleLeftIcon() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            titleLeftIcon = Icons.Share05
        )
    )
}

/**
 * Preview: Title with right icon
 */
@Preview(
    name = "Title + Right Icon",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarTitleRightIcon() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            titleRightIcon = Icons.Share06
        )
    )
}

/**
 * Preview: Title with both icons
 */
@Preview(
    name = "Title + Both Icons",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarTitleBothIcons() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            titleLeftIcon = Icons.Share05,
            titleRightIcon = Icons.Share06
        )
    )
}

// ============================================================================
// Icon Variations - Body Icons
// ============================================================================

/**
 * Preview: Body with left icon
 */
@Preview(
    name = "Body + Left Icon",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarBodyLeftIcon() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            bodyText = "Body",
            bodyLeftIcon = Icons.Share05
        )
    )
}

/**
 * Preview: Body with right icon
 */
@Preview(
    name = "Body + Right Icon",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarBodyRightIcon() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            bodyText = "Body",
            bodyRightIcon = Icons.Share06
        )
    )
}

/**
 * Preview: Body with both icons
 */
@Preview(
    name = "Body + Both Icons",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarBodyBothIcons() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            bodyText = "Body",
            bodyLeftIcon = Icons.Share05,
            bodyRightIcon = Icons.Share06
        )
    )
}

// ============================================================================
// Complete Icon Combinations
// ============================================================================

/**
 * Preview: All icons (title and body)
 */
@Preview(
    name = "All Icons",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarAllIcons() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            bodyText = "Body",
            titleLeftIcon = Icons.Share05,
            titleRightIcon = Icons.Share06,
            bodyLeftIcon = Icons.Share05,
            bodyRightIcon = Icons.Share06
        )
    )
}

// ============================================================================
// Marquee with Gradients
// ============================================================================

/**
 * Preview: Marquee with right gradient
 */
@Preview(
    name = "Marquee + Right Gradient",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarMarqueeRightGradient() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "This is a very long title that will overflow and trigger marquee effect",
            enableMarquee = true,
            gradientRight = true
        )
    )
}

/**
 * Preview: Marquee with left gradient
 */
@Preview(
    name = "Marquee + Left Gradient",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarMarqueeLeftGradient() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "This is a very long title that will overflow and trigger marquee effect",
            enableMarquee = true,
            gradientLeft = true
        )
    )
}

/**
 * Preview: Marquee with both gradients
 */
@Preview(
    name = "Marquee + Both Gradients",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarMarqueeBothGradients() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "This is a very long title that will overflow and trigger marquee effect",
            bodyText = "This is also a very long body text that extends beyond the available width",
            enableMarquee = true,
            gradientLeft = true,
            gradientRight = true
        )
    )
}

// ============================================================================
// Center-Right Slot
// ============================================================================

/**
 * Preview: With center-right slot
 */
@Preview(
    name = "With Center-Right Slot",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarWithSlot() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            bodyText = "Body"
        ),
        rightSlot = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Color(0xFF0D0D0D).copy(alpha = 0.5f),
                        RoundedCornerShape(4.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFFFF7533),
                        androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    )
            )
        }
    )
}

/**
 * Preview: With slot and icons
 */
@Preview(
    name = "With Slot + Icons",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarWithSlotAndIcons() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title",
            bodyText = "Body",
            titleLeftIcon = Icons.Share05,
            titleRightIcon = Icons.Share06
        ),
        rightSlot = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Color(0xFF0D0D0D).copy(alpha = 0.5f),
                        androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFFFF7533),
                        androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    )
            )
        }
    )
}

// ============================================================================
// Complex Combinations
// ============================================================================

/**
 * Preview: Full featured (all elements)
 */
@Preview(
    name = "Full Featured",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarFullFeatured() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "Title with icons",
            bodyText = "Body with icons",
            titleLeftIcon = Icons.Share05,
            titleRightIcon = Icons.Share06,
            bodyLeftIcon = Icons.Share05,
            bodyRightIcon = Icons.Share06,
            enableMarquee = true,
            gradientLeft = true,
            gradientRight = true
        ),
        rightSlot = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Color(0xFF0D0D0D).copy(alpha = 0.5f),
                        androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    )
                    .border(
                        1.dp,
                        Color(0xFFFF7533),
                        androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                    )
            )
        }
    )
}

/**
 * Preview: Long text with marquee
 */
@Preview(
    name = "Long Text + Marquee",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopTitleBarLongTextMarquee() {
    PopTitleBar(
        config = PopTitleBarConfig(
            titleText = "This is an extremely long title text that definitely exceeds the available container width and will trigger the marquee scrolling animation effect",
            bodyText = "This is also a very long body text that extends well beyond the normal width and will also scroll if marquee is enabled",
            enableMarquee = true,
            gradientLeft = true,
            gradientRight = true
        )
    )
}

// ============================================================================
// Combined Preview Functions
// ============================================================================

/**
 * Preview: All icon combinations
 */
@Preview(
    name = "All Icon Combinations",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewAllIconCombinations() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PreviewPopTitleBarTitleOnly()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarTitleLeftIcon()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarTitleRightIcon()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarTitleBothIcons()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarWithBody()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarAllIcons()
    }
}

/**
 * Preview: Marquee variations
 */
@Preview(
    name = "Marquee Variations",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewMarqueeVariations() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopTitleBar(
            config = PopTitleBarConfig(
                titleText = "Short title",
                enableMarquee = false
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarMarqueeRightGradient()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarMarqueeLeftGradient()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarMarqueeBothGradients()
    }
}

/**
 * Preview: Slot variations
 */
@Preview(
    name = "Slot Variations",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewSlotVariations() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopTitleBar(
            config = PopTitleBarConfig(
                titleText = "Title",
                bodyText = "Body"
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarWithSlot()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarWithSlotAndIcons()
        Spacer(modifier = Modifier.height(8.dp))
        
        PreviewPopTitleBarFullFeatured()
    }
}

