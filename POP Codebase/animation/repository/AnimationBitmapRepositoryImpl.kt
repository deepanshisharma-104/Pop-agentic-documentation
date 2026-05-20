package com.pop.components.animation.repository

import android.graphics.Bitmap
import com.google.gson.Gson
import com.pop.components.animation.data.local.AnimationBitmapDao
import com.pop.components.animation.data.local.AnimationBitmapEntity
import com.pop.components.animation.data.local.AnimationBitmapType
import com.pop.components.animation.utils.BitmapConverter
import dagger.Lazy
import timber.log.Timber
import javax.inject.Inject

/**
 * Implementation of AnimationBitmapRepository.
 * Handles bitmap storage and retrieval using Room database.
 */
internal class AnimationBitmapRepositoryImpl @Inject constructor(
    private val animationBitmapDao: Lazy<AnimationBitmapDao>
) : AnimationBitmapRepository {

    private val gson = Gson()

    companion object {
        private const val JPEG_QUALITY = 85
    }

    override suspend fun storeSplitScreenBitmaps(
        animationId: String,
        upperBitmap: Bitmap,
        lowerBitmap: Bitmap,
        splitAnchorPercent: Float
    ): Boolean {
        if (animationId.isBlank()) {
            return false
        }

        if (upperBitmap.isRecycled || lowerBitmap.isRecycled) {
            return false
        }

        return try {
            val upperBytes = BitmapConverter.bitmapToByteArray(
                upperBitmap,
                format = Bitmap.CompressFormat.JPEG,
                quality = JPEG_QUALITY
            )
            val lowerBytes = BitmapConverter.bitmapToByteArray(
                lowerBitmap,
                format = Bitmap.CompressFormat.JPEG,
                quality = JPEG_QUALITY
            )

            val metadata = gson.toJson(mapOf("splitAnchorPercent" to splitAnchorPercent.toString()))

            val entity = AnimationBitmapEntity(
                animationId = animationId,
                animationType = AnimationBitmapType.SPLIT_SCREEN,
                upperBitmapBytes = upperBytes,
                lowerBitmapBytes = lowerBytes,
                metadata = metadata
            )

            animationBitmapDao.get().insertBitmap(entity)
            true
        } catch (e: Exception) {
            Timber.tag("AnimationBitmapRepo").e(e, "Failed to store split screen bitmaps for animationId: $animationId")
            false
        }
    }

    override suspend fun getSplitScreenBitmaps(animationId: String): Pair<Bitmap, Bitmap>? {
        if (animationId.isBlank()) {
            return null
        }

        return try {
            val entity = animationBitmapDao.get().getBitmapByAnimationId(animationId)
                ?: return null

            if (entity.animationType != AnimationBitmapType.SPLIT_SCREEN) {
                return null
            }

            val upperBytes = entity.upperBitmapBytes ?: return null
            val lowerBytes = entity.lowerBitmapBytes ?: return null

            val upperBitmap = BitmapConverter.byteArrayToBitmap(upperBytes) ?: return null
            val lowerBitmap = BitmapConverter.byteArrayToBitmap(lowerBytes) ?: return null

            Pair(upperBitmap, lowerBitmap)
        } catch (e: Exception) {
            Timber.tag("AnimationBitmapRepo").e(e, "Failed to get split screen bitmaps for animationId: $animationId")
            null
        }
    }

    override suspend fun storeMaskRevealBitmap(
        animationId: String,
        bitmap: Bitmap,
        verticalOriginFraction: Float
    ): Boolean {
        if (animationId.isBlank()) {
            return false
        }

        if (bitmap.isRecycled) {
            return false
        }

        return try {
            val bitmapBytes = BitmapConverter.bitmapToByteArray(
                bitmap,
                format = Bitmap.CompressFormat.JPEG,
                quality = JPEG_QUALITY
            )

            val metadata = gson.toJson(mapOf("verticalOriginFraction" to verticalOriginFraction.toString()))

            val entity = AnimationBitmapEntity(
                animationId = animationId,
                animationType = AnimationBitmapType.MASK_REVEAL,
                fullBitmapBytes = bitmapBytes,
                metadata = metadata
            )

            animationBitmapDao.get().insertBitmap(entity)
            true
        } catch (e: Exception) {
            Timber.tag("AnimationBitmapRepo").e(e, "Failed to store mask reveal bitmap for animationId: $animationId")
            false
        }
    }

    override suspend fun getMaskRevealBitmap(animationId: String): Bitmap? {
        if (animationId.isBlank()) {
            return null
        }

        return try {
            val entity = animationBitmapDao.get().getBitmapByAnimationId(animationId)
                ?: return null

            if (entity.animationType != AnimationBitmapType.MASK_REVEAL) {
                return null
            }

            val bitmapBytes = entity.fullBitmapBytes ?: return null

            BitmapConverter.byteArrayToBitmap(bitmapBytes)
        } catch (e: Exception) {
            Timber.tag("AnimationBitmapRepo").e(e, "Failed to get mask reveal bitmap for animationId: $animationId")
            null
        }
    }

    override suspend fun deleteAnimationBitmap(animationId: String): Boolean {
        if (animationId.isBlank()) {
            return false
        }

        return try {
            val deletedCount = animationBitmapDao.get().deleteBitmapByAnimationId(animationId)
            deletedCount > 0
        } catch (e: Exception) {
            Timber.tag("AnimationBitmapRepo").e(e, "Failed to delete animation bitmap for animationId: $animationId")
            false
        }
    }

    override suspend fun clearAllBitmaps(): Int {
        return try {
            animationBitmapDao.get().deleteAllBitmaps()
        } catch (e: Exception) {
            Timber.tag("AnimationBitmapRepo").e(e, "Failed to clear all animation bitmaps")
            -1
        }
    }
}

