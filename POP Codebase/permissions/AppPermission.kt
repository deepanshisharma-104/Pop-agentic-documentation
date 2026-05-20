package com.pop.components.permissions

import android.Manifest
import com.pop.components.theme.IconName
import com.pop.components.theme.Icons

/**
 * Enum representing app permissions with their metadata.
 */
enum class AppPermission(
    val androidPermission: String,
    val title: String,
    val description: String,
    val isOptional: Boolean = false,
    val icon: IconName = Icons.DefaultCircle
) {
    CAMERA(
        androidPermission = Manifest.permission.CAMERA,
        title = "Camera",
        description = "Scan QR codes and upload photos when needed",
    ),
    PHONE(
        androidPermission = Manifest.permission.READ_PHONE_STATE,
        title = "Phone",
        description = "Collect phone number to match SIM on the device"
    ),
    SMS(
        androidPermission = Manifest.permission.READ_SMS,
        title = "SMS",
        description = "Verify your device as per RBI & NPCI guidelines",
        icon = Icons.SMS
    ),
    RECEIVE_SMS(
        androidPermission = Manifest.permission.RECEIVE_SMS,
        title = "Receive SMS",
        description = "Verify your device as per RBI & NPCI guidelines",
        icon = Icons.SMS
    ),
    SEND_SMS(
        androidPermission = Manifest.permission.SEND_SMS,
        title = "Send SMS",
        description = "Verify your device as per RBI & NPCI guidelines",
        icon = Icons.SMS
    ),
    LOCATION(
        androidPermission = Manifest.permission.ACCESS_FINE_LOCATION,
        title = "Location",
        description = "Enhance security and banking experience",
        isOptional = true
    ),
    NOTIFICATION(
        androidPermission = Manifest.permission.ACCESS_FINE_LOCATION,
        title = "Notification",
        description = "Enable notifications to receive updates",
        isOptional = true,
        icon = Icons.Notification
    )
}
