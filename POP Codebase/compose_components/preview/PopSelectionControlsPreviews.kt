package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.ds_components.PopCheckBoxAlign
import com.pop.components.ds_components.PopCheckBoxState
import com.pop.components.ds_components.PopCheckBoxV2
import com.pop.components.ds_components.PopCheckBoxWithTextV2
import com.pop.components.ds_components.PopRadio
import com.pop.components.ds_components.PopToggle
import com.pop.components.ds_components.PopToggleSize
import com.pop.components.ds_components.PopToggleState
import com.pop.components.theme.PopTheme
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun PopSelectionControlsPreview() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Radio")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopRadio(selected = false, promoted = false, enabled = true, onClick = {})
                PopRadio(selected = true, promoted = false, enabled = true, onClick = {})
                PopRadio(selected = false, promoted = false, enabled = false, onClick = {})
                PopRadio(selected = true, promoted = false, enabled = false, onClick = {})
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopRadio(selected = false, promoted = true, enabled = true, onClick = {})
                PopRadio(selected = true, promoted = true, enabled = true, onClick = {})
            }

            Spacer(Modifier.height(8.dp))
            SectionTitle("Checkbox")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulCheckBox(initial = PopCheckBoxState.Unselected, promoted = false, enabled = true)
                StatefulCheckBox(initial = PopCheckBoxState.Selected, promoted = false, enabled = true)
                StatefulCheckBox(initial = PopCheckBoxState.Unselected, promoted = false, enabled = false)
                StatefulCheckBox(initial = PopCheckBoxState.Selected, promoted = false, enabled = false)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulCheckBox(initial = PopCheckBoxState.Unselected, promoted = true, enabled = true)
                StatefulCheckBox(initial = PopCheckBoxState.Selected, promoted = true, enabled = true)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulCheckBox(initial = PopCheckBoxState.Indeterminate, promoted = false, enabled = true)
                StatefulCheckBox(initial = PopCheckBoxState.Indeterminate, promoted = true, enabled = true)
                StatefulCheckBox(initial = PopCheckBoxState.Indeterminate, promoted = false, enabled = false)
            }

            Spacer(Modifier.height(8.dp))
            SectionTitle("Checkbox with text")
            StatefulCheckBoxWithText(
                initial = PopCheckBoxState.Unselected,
                promoted = false,
                enabled = true,
                align = PopCheckBoxAlign.Left,
                title = AnnotatedString("By continuing, you agree to these Terms and Policies"),
                body = "To continue, please accept this",
            )
            StatefulCheckBoxWithText(
                initial = PopCheckBoxState.Selected,
                promoted = false,
                enabled = true,
                align = PopCheckBoxAlign.Left,
                title = AnnotatedString("By continuing, you agree to these Terms and Policies"),
                body = "Thanks for accepting this. You can now continue.",
            )
            StatefulCheckBoxWithText(
                initial = PopCheckBoxState.Unselected,
                promoted = true,
                enabled = true,
                align = PopCheckBoxAlign.Right,
                title = AnnotatedString("By continuing, you agree to these Terms and Policies"),
                body = "To continue, please accept this",
            )
            StatefulCheckBoxWithText(
                initial = PopCheckBoxState.Unselected,
                promoted = false,
                enabled = false,
                align = PopCheckBoxAlign.Left,
                title = AnnotatedString("By continuing, you agree to these Terms and Policies"),
                body = "To accept these, please complete steps above first",
            )

            Spacer(Modifier.height(8.dp))
            SectionTitle("Toggle (Large)")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulToggle(initial = PopToggleState.Off, size = PopToggleSize.Large, promoted = false, enabled = true)
                StatefulToggle(initial = PopToggleState.On, size = PopToggleSize.Large, promoted = false, enabled = true)
                StatefulToggle(initial = PopToggleState.Off, size = PopToggleSize.Large, promoted = false, enabled = false)
                StatefulToggle(initial = PopToggleState.On, size = PopToggleSize.Large, promoted = false, enabled = false)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulToggle(initial = PopToggleState.Off, size = PopToggleSize.Large, promoted = true, enabled = true)
                StatefulToggle(initial = PopToggleState.On, size = PopToggleSize.Large, promoted = true, enabled = true)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulToggle(initial = PopToggleState.Indeterminate, size = PopToggleSize.Large, promoted = false, enabled = true)
                StatefulToggle(initial = PopToggleState.Indeterminate, size = PopToggleSize.Large, promoted = true, enabled = true)
                StatefulToggle(initial = PopToggleState.Indeterminate, size = PopToggleSize.Large, promoted = false, enabled = false)
            }

            Spacer(Modifier.height(8.dp))
            SectionTitle("Toggle (Medium)")
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulToggle(initial = PopToggleState.Off, size = PopToggleSize.Medium, promoted = false, enabled = true)
                StatefulToggle(initial = PopToggleState.On, size = PopToggleSize.Medium, promoted = false, enabled = true)
                StatefulToggle(initial = PopToggleState.Off, size = PopToggleSize.Medium, promoted = false, enabled = false)
                StatefulToggle(initial = PopToggleState.On, size = PopToggleSize.Medium, promoted = false, enabled = false)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulToggle(initial = PopToggleState.Off, size = PopToggleSize.Medium, promoted = true, enabled = true)
                StatefulToggle(initial = PopToggleState.On, size = PopToggleSize.Medium, promoted = true, enabled = true)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatefulToggle(initial = PopToggleState.Indeterminate, size = PopToggleSize.Medium, promoted = false, enabled = true)
                StatefulToggle(initial = PopToggleState.Indeterminate, size = PopToggleSize.Medium, promoted = true, enabled = true)
                StatefulToggle(initial = PopToggleState.Indeterminate, size = PopToggleSize.Medium, promoted = false, enabled = false)
            }
        }
    }
}

@Composable
private fun StatefulCheckBox(
    initial: PopCheckBoxState,
    promoted: Boolean,
    enabled: Boolean,
) {
    var state by remember { mutableStateOf(initial) }
    PopCheckBoxV2(
        state = state,
        promoted = promoted,
        enabled = enabled,
        onStateChange = { state = it }
    )
}

@Composable
private fun StatefulCheckBoxWithText(
    initial: PopCheckBoxState,
    promoted: Boolean,
    enabled: Boolean,
    align: PopCheckBoxAlign,
    title: AnnotatedString,
    body: String?,
) {
    var state by remember { mutableStateOf(initial) }
    PopCheckBoxWithTextV2(
        state = state,
        promoted = promoted,
        enabled = enabled,
        align = align,
        title = title,
        body = body,
        onStateChange = { state = it }
    )
}

@Composable
private fun StatefulToggle(
    initial: PopToggleState,
    size: PopToggleSize,
    promoted: Boolean,
    enabled: Boolean,
) {
    var state by remember { mutableStateOf(initial) }
    PopToggle(
        state = state,
        size = size,
        promoted = promoted,
        enabled = enabled,
        onStateChange = { state = it }
    )
}

@Composable
private fun SectionTitle(text: String) {
    Row {
        Text(
            text = text,
            fontFamily = PopTypography.figtree,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = TextColor.Primary
        )
        Spacer(Modifier.width(8.dp))
    }
}


