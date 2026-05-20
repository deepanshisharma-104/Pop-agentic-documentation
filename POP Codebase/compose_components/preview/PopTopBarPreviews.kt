package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.ds_components.PopAppBar
import com.pop.components.ds_components.PopTopBar
import com.pop.components.models.AppBarIconSlot
import com.pop.components.models.AppBarIconSlots
import com.pop.components.models.IconAnimationType
import com.pop.components.models.MerchantImageSize
import com.pop.components.models.PopAppBarConfig
import com.pop.components.models.PopTitleBarConfig
import com.pop.components.models.PopTopBarConfig
import com.pop.components.models.StatusBarTheme
import com.pop.components.models.VisualElement
import com.pop.components.theme.Icons

/**
 * Preview functions for PopTopBar and PopAppBar components
 */

@Preview(name = "PopAppBar - Basic")
@Composable
private fun PopAppBarPreviewBasic() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
    ) {
        PopAppBar(
            config = PopAppBarConfig.default()
        )
    }
}

@Preview(name = "PopAppBar - With Actions")
@Composable
private fun PopAppBarPreviewWithActions() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
    ) {
        PopAppBar(
            config = PopAppBarConfig(
                title = "Title",
                navigationIcon = Icons.Share05,
                onNavigationClick = {},
                iconSlots = AppBarIconSlots(
                    slot1 = AppBarIconSlot(
                        icon = Icons.Share06,
                        onClick = {},
                        contentDescription = "Share",
                        animationType = IconAnimationType.Dissolve
                    )
                )
            )
        )
    }
}

@Preview(name = "PopTopBar - No Merchant Image")
@Composable
private fun PopTopBarPreviewNoMerchant() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
    ) {
        PopTopBar(
            config = PopTopBarConfig.default(
                titleText = "Payments",
                bodyText = "UPI · Cards · Wallets"
            )
        )
    }
}

@Preview(name = "PopTopBar - Large Merchant")
@Composable
private fun PopTopBarPreviewLargeMerchant() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
    ) {
        PopTopBar(
            config = PopTopBarConfig(
                titleBarConfig = PopTitleBarConfig.default(
                    titleText = "Merchant Name",
                    bodyText = "Category · Location"
                ),
                merchantImage = VisualElement.buildFrom("https://t4.ftcdn.net/jpg/04/90/84/21/360_F_490842185_Ll9ATc00Cnwqv8AcRGZMPdpoHQYCKJor.jpg"),
                merchantImageSize = MerchantImageSize.Large,
                statusBarTheme = StatusBarTheme.Dark,
                onMerchantImageClick = {
                    // Handle merchant image click
                    println("Merchant image clicked - Large")
                }
            )
        )
    }
}

@Preview(name = "PopTopBar - Medium Merchant")
@Composable
private fun PopTopBarPreviewMediumMerchant() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
    ) {
        PopTopBar(
            config = PopTopBarConfig(
                titleBarConfig = PopTitleBarConfig.default(
                    titleText = "Merchant Name",
                    bodyText = "Category · Location"
                ),
                merchantImage = VisualElement.buildFrom("https://t4.ftcdn.net/jpg/04/90/84/21/360_F_490842185_Ll9ATc00Cnwqv8AcRGZMPdpoHQYCKJor.jpg"),
                merchantImageSize = MerchantImageSize.Medium,
                statusBarTheme = StatusBarTheme.Dark,
                onMerchantImageClick = {
                    // Handle merchant image click
                    println("Merchant image clicked - Medium")
                }
            )
        )
    }
}

@Preview(name = "PopTopBar - Small Merchant")
@Composable
private fun PopTopBarPreviewSmallMerchant() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
    ) {
        PopTopBar(
            config = PopTopBarConfig(
                titleBarConfig = PopTitleBarConfig.default(
                    titleText = "Merchant Name",
                    bodyText = "Category"
                ),
                merchantImage = VisualElement.buildFrom("https://t4.ftcdn.net/jpg/04/90/84/21/360_F_490842185_Ll9ATc00Cnwqv8AcRGZMPdpoHQYCKJor.jpg"),
                merchantImageSize = MerchantImageSize.Small,
                statusBarTheme = StatusBarTheme.Dark,
                onMerchantImageClick = {
                    // Handle merchant image click
                    println("Merchant image clicked - Small")
                }
            )
        )
    }
}


