package com.pop.components.animation.repository

import android.graphics.Bitmap

/**
 * Repository interface for animation bitmap operations.
 * Abstracts bitmap storage and retrieval from the rest of the application.
 */
interface AnimationBitmapRepository {
    /**
     * Stores split screen bitmaps (upper and lower halves) in the database.
     *
     * @param animationId Unique identifier for this animation
     * @param upperBitmap Upper half of the screen
     * @param lowerBitmap Lower half of the screen
     * @param splitAnchorPercent The percentage where the screen was split (0.0 to 1.0)
     * @return true if storage was successful, false otherwise
     */
    suspend fun storeSplitScreenBitmaps(
        animationId: String,
        upperBitmap: Bitmap,
        lowerBitmap: Bitmap,
        splitAnchorPercent: Float
    ): Boolean

    /**
     * Retrieves split screen bitmaps from the database.
     *
     * @param animationId Unique identifier for this animation
     * @return Pair of (upperBitmap, lowerBitmap) if found, null otherwise
     */
    suspend fun getSplitScreenBitmaps(animationId: String): Pair<Bitmap, Bitmap>?

    /**
     * Stores mask reveal bitmap in the database.
     *
     * @param animationId Unique identifier for this animation
     * @param bitmap Full screen bitmap
     * @param verticalOriginFraction The vertical origin fraction for the mask reveal
     * @return true if storage was successful, false otherwise
     */
    suspend fun storeMaskRevealBitmap(
        animationId: String,
        bitmap: Bitmap,
        verticalOriginFraction: Float
    ): Boolean

    /**
     * Retrieves mask reveal bitmap from the database.
     *
     * @param animationId Unique identifier for this animation
     * @return Bitmap if found, null otherwise
     */
    suspend fun getMaskRevealBitmap(animationId: String): Bitmap?

    /**
     * Deletes an animation bitmap from the database.
     *
     * @param animationId Unique identifier for this animation
     * @return true if deletion was successful, false otherwise
     */
    suspend fun deleteAnimationBitmap(animationId: String): Boolean

    /**
     * Clears all animation bitmaps from the database.
     * Called on app exit to ensure new sessions start fresh.
     *
     * @return Number of bitmaps deleted, or -1 if an error occurred
     */
    suspend fun clearAllBitmaps(): Int
}

