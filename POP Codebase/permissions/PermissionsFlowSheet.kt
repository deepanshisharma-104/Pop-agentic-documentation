package com.pop.components.permissions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.AvatarIconFill
import com.pop.components.ds_components.AvatarSize
import com.pop.components.ds_components.AvatarType
import com.pop.components.ds_components.ButtonLoadingState
import com.pop.components.ds_components.ButtonState
import com.pop.components.ds_components.ButtonVariant
import com.pop.components.ds_components.ButtonWidthType
import com.pop.components.ds_components.PopAvatar
import com.pop.components.ds_components.PopBottomSheet
import com.pop.components.ds_components.PopButtonV2
import com.pop.components.models.BottomSheetBackgroundType
import com.pop.components.models.BottomSheetGradientShape
import com.pop.components.models.PopBottomSheetConfig
import com.pop.components.models.VisualElement
import com.pop.components.theme.Icons
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor
import com.pop.compose_components.R
import kotlinx.coroutines.delay

/**
 * A bottom sheet that handles requesting multiple permissions sequentially.
 *
 * @param title The title displayed at the top of the sheet
 * @param description The description displayed below the title
 * @param permissions List of permissions to request
 * @param onAllGranted Callback when all mandatory permissions are granted
 * @param onDismiss Callback when the sheet is dismissed
 */
@Composable
fun PermissionFlowBottomSheet(
    title: String = "Permissions required",
    description: String = "We need some permission to continue",
    permissions: List<AppPermission>,
    onAllGranted: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val context = LocalContext.current

    // Track granted status for each permission
    var grantedPermissions by remember {
        mutableStateOf(context.getGrantedPermissions(permissions))
    }

    // Track denied permissions (user explicitly denied)
    var deniedPermissions by remember { mutableStateOf<Set<AppPermission>>(emptySet()) }

    // Track if user has attempted permissions (after first attempt, go to settings if denied)
    var attemptCount by remember { mutableIntStateOf(0) }

    // Track current permission index being requested
    var currentIndex by remember { mutableIntStateOf(-1) }

    // When set, triggers the animated exit and calls this callback after animation completes
    var dismissCallback by remember { mutableStateOf<(() -> Unit)?>(null) }

    // Check if all mandatory permissions are granted
    val allMandatoryGranted = permissions
        .filter { !it.isOptional }
        .all { it in grantedPermissions }

    // Check if any mandatory permission was denied
    val hasMandatoryDenied = permissions
        .filter { !it.isOptional }
        .any { it in deniedPermissions }

    // If any mandatory permission denied after first attempt, redirect to Settings
    val shouldGoToSettings = attemptCount >= 1 && hasMandatoryDenied

    /* ---------------- Settings Launcher ---------------- */

    val settingsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // Refresh permission states when returning from settings
        val newGranted = context.getGrantedPermissions(permissions)
        grantedPermissions = newGranted

        // Clear denied states for permissions that are now granted
        deniedPermissions = deniedPermissions - newGranted

        // Reset attempt count if all mandatory are now granted
        val allMandatoryNowGranted = permissions
            .filter { !it.isOptional }
            .all { it in newGranted }
        if (allMandatoryNowGranted) {
            attemptCount = 0
        }
    }

    /* ---------------- Permission Launcher ---------------- */

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val currentPermission = permissions.getOrNull(currentIndex)

        if (currentPermission != null) {
            if (isGranted) {
                grantedPermissions = grantedPermissions + currentPermission
                deniedPermissions = deniedPermissions - currentPermission
            } else {
                deniedPermissions = deniedPermissions + currentPermission
            }
        }

        // Move to next permission
        currentIndex++
    }

    /* ---------------- Auto-request next permission ---------------- */

    LaunchedEffect(currentIndex) {
        if (currentIndex < 0) return@LaunchedEffect

        // Small delay for smooth UI
        delay(300)

        // Find next ungranted permission
        val nextPermission = permissions
            .drop(currentIndex)
            .firstOrNull { it !in grantedPermissions }

        if (nextPermission != null) {
            currentIndex = permissions.indexOf(nextPermission)
            permissionLauncher.launch(nextPermission.androidPermission)
        } else {
            currentIndex = -1
            if (allMandatoryGranted) {
                dismissCallback = onAllGranted
            }
        }
    }


    PopBottomSheet(
        showSheet = dismissCallback == null,
        onDismissRequest = dismissCallback ?: onDismiss,
        config = PopBottomSheetConfig(
            cornerRadius = 24.dp,
            isDraggable = false,
            isCancellable = true,
            showFloatingCloseButton = true,
            backgroundType = BottomSheetBackgroundType.Gradient(
                shape = BottomSheetGradientShape.Squircle
            ),
            topVisualElement = VisualElement.buildFrom(
                resId = R.drawable.ic_percent_bg_blue,
                heightDp = 80,
                widthDp = 80
            ),
        ),
        onCloseButtonClick = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 12.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                // Header
                PermissionHeader(title = title, description = description)

                // Permission List
                permissions.forEach { permission ->
                    PermissionRow(
                        permission = permission,
                        isGranted = permission in grantedPermissions,
                        isDenied = permission in deniedPermissions,
                        isLast = permissions.last() == permission
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Error message when mandatory permission denied
            if (hasMandatoryDenied) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                ) {
                    PopAvatar(
                        type = AvatarType.Icon(
                            fill = AvatarIconFill.Primary50Percent,
                            icon = Icons.InfoSquare,
                            iconTint = TextColor.Destructive
                        ),
                        size = AvatarSize.Medium
                    )

                    Text(
                        text = if (shouldGoToSettings)
                            "Permissions denied. Please enable from Settings."
                        else
                            "Uh-oh! You have denied a mandatory permission to continue",
                        style = PopTypography.figtreeStyles.labelSmall,
                        color = TextColor.Destructive
                    )
                }
            }

            // CTA Button
            PopButtonV2(
                modifier = Modifier.fillMaxWidth(),
                text = when {
                    allMandatoryGranted -> ""
                    shouldGoToSettings -> "Open Settings"
                    hasMandatoryDenied -> "Try Again"
                    else -> "Allow Permissions"
                },
                variant = ButtonVariant.Primary,
                onClick = {
                    when {
                        allMandatoryGranted -> dismissCallback = onAllGranted
                        shouldGoToSettings -> {
                            // Open app settings
                            val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                            settingsLauncher.launch(intent)
                        }

                        else -> {
                            // Start requesting permissions (will go to Settings if denied)
                            attemptCount++
                            deniedPermissions = emptySet()
                            currentIndex = 0
                        }
                    }
                },
                state = if (allMandatoryGranted) ButtonState.Loading else ButtonState.Active,
                buttonLoadingState = if (allMandatoryGranted) ButtonLoadingState.Success else null,
                widthType = ButtonWidthType.Fill
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionFlowPreview() {
    PermissionFlowBottomSheet(
        permissions = listOf(
            AppPermission.CAMERA,
            AppPermission.PHONE,
            AppPermission.SMS,
            AppPermission.LOCATION
        )
    )
}

/*
 * ============================================================================
 * PERMISSION FLOW BOTTOM SHEET - USAGE GUIDE
 * ============================================================================
 *
 * This component handles requesting multiple permissions sequentially with a
 * beautiful bottom sheet UI. It supports:
 * - Sequential permission requests (one after another)
 * - Automatic Settings redirect if permissions are denied
 * - Success state animation when all permissions are granted
 * - Optional permissions support
 *
 * ============================================================================
 * USAGE EXAMPLE 1: Basic Usage with PermissionFlowHandler (Recommended)
 * ============================================================================
 *
 * This is the simplest approach - it automatically checks if permissions are
 * already granted and skips showing the sheet if not needed.
 *
 * ```kotlin
 * @Composable
 * fun MyScreen() {
 *     var showPermissionFlow by remember { mutableStateOf(false) }
 *
 *     // Your UI
 *     PopButtonV2(
 *         text = "Continue",
 *         onClick = { showPermissionFlow = true }
 *     )
 *
 *     // Permission handler - shows sheet only if permissions are missing
 *     if (showPermissionFlow) {
 *         PermissionFlowHandler(
 *             title = "Permissions Required",
 *             description = "We need some permissions to continue",
 *             permissions = listOf(
 *                 AppPermission.SMS,
 *                 AppPermission.PHONE,
 *                 AppPermission.LOCATION  // Optional permission
 *             ),
 *             onAllGranted = {
 *                 showPermissionFlow = false
 *                 // Navigate to next screen or continue flow
 *                 navController.navigate("next_screen")
 *             },
 *             onDismiss = {
 *                 showPermissionFlow = false
 *             }
 *         )
 *     }
 * }
 * ```
 *
 * ============================================================================
 * USAGE EXAMPLE 2: Direct PermissionFlowBottomSheet Usage
 * ============================================================================
 *
 * Use this when you want more control or always want to show the sheet.
 *
 * ```kotlin
 * @Composable
 * fun MyScreen() {
 *     var showPermissionSheet by remember { mutableStateOf(false) }
 *
 *     // Your clickable composable
 *     Card(
 *         onClick = { showPermissionSheet = true }
 *     ) {
 *         Text("Scan QR Code")
 *     }
 *
 *     // Permission bottom sheet
 *     if (showPermissionSheet) {
 *         PermissionFlowBottomSheet(
 *             title = "Camera Access Needed",
 *             description = "Allow camera to scan QR codes",
 *             permissions = listOf(
 *                 AppPermission.CAMERA,
 *                 AppPermission.SMS
 *             ),
 *             onAllGranted = {
 *                 showPermissionSheet = false
 *                 // Proceed with your action
 *                 openQrScanner()
 *             },
 *             onDismiss = {
 *                 showPermissionSheet = false
 *             }
 *         )
 *     }
 * }
 * ```
 *
 * ============================================================================
 * USAGE EXAMPLE 3: Check Permissions Before Showing Sheet
 * ============================================================================
 *
 * Use this approach when you want to check permissions in onClick and only
 * show the sheet if needed.
 *
 * ```kotlin
 * @Composable
 * fun MyScreen() {
 *     val context = LocalContext.current
 *     var showPermissionSheet by remember { mutableStateOf(false) }
 *
 *     val requiredPermissions = listOf(
 *         AppPermission.SMS,
 *         AppPermission.PHONE
 *     )
 *
 *     PopButtonV2(
 *         text = "Transfer Money",
 *         onClick = {
 *             // Check if all mandatory permissions are already granted
 *             if (context.areAllMandatoryPermissionsGranted(requiredPermissions)) {
 *                 // Permissions already granted, proceed directly
 *                 navigateToTransfer()
 *             } else {
 *                 // Show permission sheet
 *                 showPermissionSheet = true
 *             }
 *         }
 *     )
 *
 *     if (showPermissionSheet) {
 *         PermissionFlowBottomSheet(
 *             permissions = requiredPermissions,
 *             onAllGranted = {
 *                 showPermissionSheet = false
 *                 navigateToTransfer()
 *             },
 *             onDismiss = {
 *                 showPermissionSheet = false
 *             }
 *         )
 *     }
 * }
 * ```
 *
 * ============================================================================
 * AVAILABLE PERMISSIONS (AppPermission enum)
 * ============================================================================
 *
 * - AppPermission.CAMERA      - Camera access for QR scanning
 * - AppPermission.PHONE       - Phone state for SIM verification
 * - AppPermission.SMS         - SMS for OTP verification (RBI/NPCI)
 * - AppPermission.LOCATION    - Location for security (optional)
 * - AppPermission.NOTIFICATION - Notification for updates (optional)
 *
 * ============================================================================
 * PERMISSION FLOW BEHAVIOR
 * ============================================================================
 *
 * 1. User clicks "Allow Permissions"
 *    → Requests each permission sequentially
 *
 * 2. If user GRANTS all permissions
 *    → Shows success animation
 *    → Calls onAllGranted()
 *
 * 3. If user DENIES any mandatory permission
 *    → Shows error message
 *    → Button changes to "Open Settings"
 *    → Redirects to app settings for manual permission grant
 *
 * 4. User returns from Settings
 *    → Automatically refreshes permission states
 *    → Shows success if all granted
 *
 * ============================================================================
 */
