package com.pop.components.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * Extension function to check if a permission is granted.
 */
fun Context.isPermissionGranted(permission: AppPermission): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission.androidPermission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.givenAllPermissions(vararg permissions: String): Boolean {
    return permissions.all { permission ->
        ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}

/**
 * Extension function to check if all permissions in the list are granted.
 */
fun Context.areAllPermissionsGranted(permissions: List<AppPermission>): Boolean {
    return permissions.all { isPermissionGranted(it) }
}

/**
 * Extension function to get list of granted permissions.
 */
fun Context.getGrantedPermissions(permissions: List<AppPermission>): Set<AppPermission> {
    return permissions.filter { isPermissionGranted(it) }.toSet()
}

/**
 * Extension function to check if all mandatory permissions are granted.
 */
fun Context.areAllMandatoryPermissionsGranted(permissions: List<AppPermission>): Boolean {
    return permissions
        .filter { !it.isOptional }
        .all { isPermissionGranted(it) }
}

/**
 * Helper function that checks permissions and executes appropriate callback.
 * If all mandatory permissions are granted, executes [onAllGranted] immediately.
 * Otherwise, returns false indicating that permission sheet should be shown.
 *
 * @param permissions List of permissions to check
 * @param onAllGranted Callback when all mandatory permissions are already granted
 * @return true if all mandatory permissions are granted (no need to show sheet), false otherwise
 */
fun Context.checkPermissionsAndProceed(
    permissions: List<AppPermission>,
    onAllGranted: () -> Unit
): Boolean {
    return if (areAllMandatoryPermissionsGranted(permissions)) {
        onAllGranted()
        true
    } else {
        false
    }
}

/**
 * A composable that handles permission flow intelligently.
 * - If all mandatory permissions are already granted, calls [onAllGranted] immediately without showing the sheet.
 * - If permissions are missing, shows the permission bottom sheet.
 *
 * Usage:
 * ```
 * var showPermissionFlow by remember { mutableStateOf(false) }
 *
 * if (showPermissionFlow) {
 *     PermissionFlowHandler(
 *         permissions = listOf(AppPermission.SMS, AppPermission.PHONE),
 *         onAllGranted = {
 *             showPermissionFlow = false
 *             // Navigate to next screen
 *         },
 *         onDismiss = {
 *             showPermissionFlow = false
 *         }
 *     )
 * }
 * ```
 */
@Composable
fun PermissionFlowHandler(
    title: String = "Permissions required",
    description: String = "We need some permission to continue",
    permissions: List<AppPermission>,
    onAllGranted: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    // Check if all mandatory permissions are already granted
    val allMandatoryGranted = context.areAllMandatoryPermissionsGranted(permissions)

    if (allMandatoryGranted) {
        // All permissions already granted, call onAllGranted and skip showing sheet
        LaunchedEffect(Unit) {
            onAllGranted()
        }
    } else {
        // Show permission bottom sheet
        PermissionFlowBottomSheet(
            title = title,
            description = description,
            permissions = permissions,
            onAllGranted = onAllGranted,
            onDismiss = onDismiss
        )
    }
}
