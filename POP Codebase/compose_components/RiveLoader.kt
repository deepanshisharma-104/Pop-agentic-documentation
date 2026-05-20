package com.pop.components.compose_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.rive.runtime.kotlin.RiveAnimationView
import com.pop.components.theme.PopColors

@Composable
fun RiveLoader(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    riveResource: Int,
    size: Dp = 200.dp
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PopColors.Misc.BlackOpacity33)
                .clickable(enabled = false) {}, // Block touches while visible
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    RiveAnimationView(context).apply {
                        setRiveResource(riveResource)
                        // Configuration is handled via XML attributes in the Rive file
                        // The animation will auto-play based on the Rive file settings
                    }
                },
                modifier = Modifier.size(size),
                update = { view ->
                    if (isVisible) {
                        view.play()
                    } else {
                        view.stop()
                    }
                }
            )
        }
    }
}

