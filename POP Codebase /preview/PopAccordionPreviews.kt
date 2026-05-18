package com.pop.components.compose_components.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.PopAccordion
import com.pop.components.models.AccordionConfig
import com.pop.components.models.AccordionSeparatorColor
import com.pop.components.models.AccordionSeparatorStyle
import com.pop.components.models.PopTitleBarConfig
import com.pop.components.theme.PopColors

// ========== Basic Accordion Previews ==========

@Preview(showBackground = true, name = "Basic - Collapsed")
@Composable
fun AccordionBasicCollapsedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            showBorder = false
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "Basic - Expanded")
@Composable
fun AccordionBasicExpandedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            showBorder = false
        ).copy(isInitiallyExpanded = true),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "With Body - Collapsed")
@Composable
fun AccordionWithBodyCollapsedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = false
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "With Body - Expanded")
@Composable
fun AccordionWithBodyExpandedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = false
        ).copy(isInitiallyExpanded = true),
        content = {
            Text("Content here")
        }
    )
}

// ========== Border Variations ==========

@Preview(showBackground = true, name = "With Border - Collapsed")
@Composable
fun AccordionWithBorderCollapsedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            showBorder = true
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "With Border - Expanded")
@Composable
fun AccordionWithBorderExpandedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            showBorder = true
        ).copy(isInitiallyExpanded = true),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "With Border and Body - Collapsed")
@Composable
fun AccordionWithBorderAndBodyCollapsedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = true
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "With Border and Body - Expanded")
@Composable
fun AccordionWithBorderAndBodyExpandedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = true
        ).copy(isInitiallyExpanded = true),
        content = {
            Text("Content here")
        }
    )
}

// ========== Separator Style Variations ==========

@Preview(showBackground = true, name = "Full Width Separator - Expanded")
@Composable
fun AccordionFullWidthSeparatorPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(isInitiallyExpanded = true),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "Dashed Separator - Expanded")
@Composable
fun AccordionDashedSeparatorPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            separatorStyle = AccordionSeparatorStyle.Dashed
        ).copy(isInitiallyExpanded = true),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "No Separator - Expanded")
@Composable
fun AccordionNoSeparatorPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(
            isInitiallyExpanded = true,
            showSeparator = false
        ),
        content = {
            Text("Content here")
        }
    )
}

// ========== Separator Color Variations ==========

@Preview(showBackground = true, name = "Primary Separator Color")
@Composable
fun AccordionPrimarySeparatorColorPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(
            isInitiallyExpanded = true,
            separatorColor = AccordionSeparatorColor.Primary
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "Secondary Separator Color")
@Composable
fun AccordionSecondarySeparatorColorPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(
            isInitiallyExpanded = true,
            separatorColor = AccordionSeparatorColor.Secondary
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "Custom Separator Color")
@Composable
fun AccordionCustomSeparatorColorPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(
            isInitiallyExpanded = true,
            separatorColor = AccordionSeparatorColor.Custom,
            customSeparatorColor = PopColors.Orange.O9
        ),
        content = {
            Text("Content here")
        }
    )
}

// ========== Complex Combinations ==========

@Preview(showBackground = true, name = "Border + Dashed Separator + Body")
@Composable
fun AccordionComplex1Preview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = true,
            separatorStyle = AccordionSeparatorStyle.Dashed
        ).copy(
            isInitiallyExpanded = true,
            separatorColor = AccordionSeparatorColor.Secondary
        ),
        content = {
            Column {
                Text("Line 1")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Line 2")
            }
        }
    )
}

@Preview(showBackground = true, name = "No Border + Full Width Separator + Body")
@Composable
fun AccordionComplex2Preview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = false,
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(
            isInitiallyExpanded = true,
            separatorColor = AccordionSeparatorColor.Primary
        ),
        content = {
            Column {
                Text("Line 1")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Line 2")
            }
        }
    )
}

@Preview(showBackground = true, name = "Border + No Separator + Body")
@Composable
fun AccordionComplex3Preview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = true,
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(
            isInitiallyExpanded = true,
            showSeparator = false
        ),
        content = {
            Column {
                Text("Line 1")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Line 2")
            }
        }
    )
}

@Preview(showBackground = true, name = "Long Title with Border")
@Composable
fun AccordionLongTitlePreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "This is a very long title that might overflow and need marquee",
            showBorder = true
        ).copy(isInitiallyExpanded = true),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "Long Content")
@Composable
fun AccordionLongContentPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            showBorder = true
        ).copy(isInitiallyExpanded = true),
        content = {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text("This is a longer content section that demonstrates how the accordion handles multiple lines of content.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Second paragraph of content.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Third paragraph of content.")
            }
        }
    )
}

// ========== All States Combined ==========

@Preview(showBackground = true, name = "All Features - Collapsed")
@Composable
fun AccordionAllFeaturesCollapsedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = true,
            separatorStyle = AccordionSeparatorStyle.Dashed
        ).copy(
            separatorColor = AccordionSeparatorColor.Secondary
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "All Features - Expanded")
@Composable
fun AccordionAllFeaturesExpandedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = true,
            separatorStyle = AccordionSeparatorStyle.Dashed
        ).copy(
            isInitiallyExpanded = true,
            separatorColor = AccordionSeparatorColor.Secondary
        ),
        content = {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text("Content line 1")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Content line 2")
            }
        }
    )
}

// ========== Background Variations ==========

@Preview(showBackground = true, name = "With Background - Collapsed with Right slot")
@Composable
fun AccordionWithBackgroundCollapsedPreview() {
    PopAccordion(
        config = AccordionConfig(
            titleBarConfig = PopTitleBarConfig.default(
                titleText = "Title",
            ),
            setBg = true
        ),
        titleBarRightSlot = {
            Text("Right Slot")
        },
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, name = "With Background - Expanded")
@Composable
fun AccordionWithBackgroundExpandedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = false
        ).copy(
            setBg = true,
            isInitiallyExpanded = true
        ),
        content = {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text("Content line 1")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Content line 2")
            }
        }
    )
}

@Preview(showBackground = true, name = "Background + Border - Collapsed")
@Composable
fun AccordionWithBackgroundAndBorderCollapsedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            showBorder = true
        ).copy(
            setBg = true
        ),
        content = {
            Text("Content here")
        }
    )
}

@Preview(showBackground = true, showSystemUi = true, name = "Background + Border - Expanded")
@Composable
fun AccordionWithBackgroundAndBorderExpandedPreview() {
    PopAccordion(
        config = AccordionConfig.default(
            title = "Title",
            body = "Body text",
            showBorder = true,
            separatorStyle = AccordionSeparatorStyle.FullWidth
        ).copy(
            setBg = true,
            isInitiallyExpanded = true,
            separatorColor = AccordionSeparatorColor.Secondary
        ),
        content = {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text("Content line 1")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Content line 2")
            }
        }
    )
}
