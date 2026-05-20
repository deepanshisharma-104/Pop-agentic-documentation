package com.pop.components.compose_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.PopColors
import com.pop.components.theme.SurfaceColors
import com.pop.components.theme.TextColors
import com.pop.compose_components.R
import kotlinx.coroutines.launch

@Composable
fun CommonBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    headerImage: Int? = null,
    leftButtonText: String,
    rightButtonText: String,
    showCrossIcon: Boolean = true,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
)
{
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), // Dark background similar to image
        shape = RoundedCornerShape(16.dp), // Rounded top corners
        color = PopColors.Misc.BottomSheetSurfaceColor // Explicitly setting surface color
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            headerImage?.let {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(149.dp),
                    painter = painterResource(it),
                    contentDescription = "Header Image",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    // style = typography.h6, // Replace with your theme's typography
                    style = FlashTypography.h2, // Example
                    color = PopColors.Neutral.N12,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                if (showCrossIcon) {
                    IconButton(onClick = onDismissRequest) {
                        Image(
                            painter = painterResource(R.drawable.ic_popup_close),
                            contentDescription = "Close", // Accessibility
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp)) // Spacing from your theme

            Text(
                text = description,
                // style = typography.body1, // Replace with your theme's typography
                style = FlashTypography.body2, // Example
                color = TextColors.Secondary.Default,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp)) // Spacing from your theme

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Spacing between buttons
            ) {
                PopButton(
                    text = leftButtonText,
                    onClick = onLeftButtonClick,
                    modifier = Modifier.weight(1f),
                    variant = FlashButtonVariant.Tertiary, // Based on image: "No" button is less prominent
                    // size = FlashButtonSize.Medium, // Adjust as needed
                    isFullWidth = true,
                    // You might need to adjust PopButton to allow custom colors
                    // or add a new variant if Tertiary doesn't match the greyish look
                )
                PopButton(
                    text = rightButtonText,
                    onClick = onRightButtonClick,
                    modifier = Modifier.weight(1f),
                    variant = FlashButtonVariant.Primary, // Based on image: "Yes" button is prominent
                    // size = FlashButtonSize.Medium, // Adjust as needed
                    isFullWidth = true
                )
            }
            Spacer(modifier = Modifier.height(8.dp)) // Bottom padding
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBottomSheetWrapper(
    modifier: Modifier,
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    description: String,
    headerImage: Int? = null,
    showCrossIcon: Boolean = true,
    leftButtonText: String,
    rightButtonText: String,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = Color.Transparent, // Let your Surface handle styling
            dragHandle = {}
        ) {
            CommonBottomSheet(
                title = title,
                description = description,
                leftButtonText = leftButtonText,
                rightButtonText = rightButtonText,
                showCrossIcon = showCrossIcon,
                headerImage = headerImage,
                onLeftButtonClick = {
                    onLeftButtonClick()
                    onDismissRequest()
                },
                onRightButtonClick = {
                    onRightButtonClick()
                    onDismissRequest()
                },
                onDismissRequest = onDismissRequest,
                modifier = modifier
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CommonBottomSheetPreview() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    //bottomsheet
//    if (showBottomSheet) {
//        ModalBottomSheet(
//            onDismissRequest = {
//                showBottomSheet = false
//            },
//            sheetState = sheetState,
//            containerColor = Color.Transparent, // To let CommonBottomSheet control its own background
//            tonalElevation = 0.dp // Remove default elevation if CommonBottomSheet handles it
//        ) {}
    // Sheet content
    CommonBottomSheet(
        title = "Disable UPI number",
        description = "Are you sure you want to disable your UPI number\n9866555822 ?",
        leftButtonText = "No",
        rightButtonText = "Yes",
        onLeftButtonClick = {
            // Handle "No" click
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
            // Add your logic here
        },
        onRightButtonClick = {
            // Handle "Yes" click
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
            // Add your logic here
        },
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWrapper(
    showSheet: Boolean = true,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            scrimColor = SurfaceColors.Overlay,
            containerColor = Color.Transparent, // Let your Surface handle styling
            dragHandle = {}
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Dark background similar to image
                shape = RoundedCornerShape(16.dp), // Rounded top corners
                color = PopColors.Misc.BottomSheetSurfaceColor // Explicitly setting surface color
            ) {
                content()
            }
        }
    }
}
