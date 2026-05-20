package com.pop.components.animation.data.local

/**
 * Enum representing different types of animation bitmaps that can be stored.
 * This allows the database to support multiple animation types in a single table.
 */
enum class AnimationBitmapType {
    /**
     * Split Screen animation type.
     * Stores upper and lower bitmap halves for split-screen transitions.
     */
    SPLIT_SCREEN,

    /**
     * Mask Reveal animation type.
     * Stores a single full-screen bitmap for mask reveal transitions.
     */
    MASK_REVEAL
}


