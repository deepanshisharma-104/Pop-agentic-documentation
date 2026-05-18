// Copyright 2023, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

package com.pop.components.ds_components.scrim

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo

/**
 * Draw the 'scrim' effect behind the attached node using a pre-configured [VisualEffect].
 *
 * ```
 * Modifier.scrimEffect(state) {
 *   visualEffect = BlurVisualEffect().apply {
 *     blurRadius = 20.dp
 *   }
 * }
 * ```
 *
 * @param state The [ScrimState] to observe for background content.
 * @param block Optional configuration block for additional effect scope properties.
 */
@OptIn(ExperimentalScrimApi::class)
@Stable
fun Modifier.scrimEffect(
    state: ScrimState?,
    block: (ScrimEffectScope.() -> Unit)? = null,
): Modifier = this then ScrimEffectNodeElement(state = state, block = block)

/**
 * Draw the 'scrim' effect, using this node's content as the source, with a pre-configured [VisualEffect].
 *
 * @param block Optional configuration block for additional effect scope properties.
 */
@OptIn(ExperimentalScrimApi::class)
@Stable
fun Modifier.scrimEffect(
    block: (ScrimEffectScope.() -> Unit)? = null,
): Modifier = this then ScrimEffectNodeElement(state = null, block = block)

@OptIn(ExperimentalScrimApi::class)
private data class ScrimEffectNodeElement @OptIn(ExperimentalScrimApi::class) constructor(
    val state: ScrimState?,
    val block: (ScrimEffectScope.() -> Unit)? = null,
) : ModifierNodeElement<ScrimEffectNode>() {

    override fun create(): ScrimEffectNode = ScrimEffectNode(
        state = state,
        block = block,
    )

    override fun update(node: ScrimEffectNode) {
        node.state = state
        node.block = block
        node.update()
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "ScrimEffect"
    }
}

