package com.pop.components.animation.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for storing animation bitmaps.
 * 
 * This generic entity supports multiple animation types:
 * - SPLIT_SCREEN: Uses upperBitmapBytes and lowerBitmapBytes
 * - MASK_REVEAL: Uses fullBitmapBytes
 * 
 * The metadata field stores type-specific configuration as JSON.
 */
@Entity(tableName = "animation_bitmaps")
data class AnimationBitmapEntity(
    @PrimaryKey
    val animationId: String,
    
    /**
     * Type of animation this bitmap belongs to.
     */
    val animationType: AnimationBitmapType,
    
    /**
     * Upper half bitmap for SPLIT_SCREEN animations.
     * Null for other animation types.
     */
    val upperBitmapBytes: ByteArray? = null,
    
    /**
     * Lower half bitmap for SPLIT_SCREEN animations.
     * Null for other animation types.
     */
    val lowerBitmapBytes: ByteArray? = null,
    
    /**
     * Full screen bitmap for MASK_REVEAL animations.
     * Null for other animation types.
     */
    val fullBitmapBytes: ByteArray? = null,
    
    /**
     * JSON string containing type-specific metadata.
     * For SPLIT_SCREEN: {"splitAnchorPercent": 0.5}
     * For MASK_REVEAL: {"verticalOriginFraction": 0.55}
     */
    val metadata: String? = null,
    
    /**
     * Timestamp when this bitmap was created (for cleanup purposes).
     */
    val createdAt: Long = System.currentTimeMillis()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnimationBitmapEntity

        if (animationId != other.animationId) return false
        if (animationType != other.animationType) return false
        if (upperBitmapBytes != null) {
            if (other.upperBitmapBytes == null) return false
            if (!upperBitmapBytes.contentEquals(other.upperBitmapBytes)) return false
        } else if (other.upperBitmapBytes != null) return false
        if (lowerBitmapBytes != null) {
            if (other.lowerBitmapBytes == null) return false
            if (!lowerBitmapBytes.contentEquals(other.lowerBitmapBytes)) return false
        } else if (other.lowerBitmapBytes != null) return false
        if (fullBitmapBytes != null) {
            if (other.fullBitmapBytes == null) return false
            if (!fullBitmapBytes.contentEquals(other.fullBitmapBytes)) return false
        } else if (other.fullBitmapBytes != null) return false
        if (metadata != other.metadata) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = animationId.hashCode()
        result = 31 * result + animationType.hashCode()
        result = 31 * result + (upperBitmapBytes?.contentHashCode() ?: 0)
        result = 31 * result + (lowerBitmapBytes?.contentHashCode() ?: 0)
        result = 31 * result + (fullBitmapBytes?.contentHashCode() ?: 0)
        result = 31 * result + (metadata?.hashCode() ?: 0)
        result = 31 * result + createdAt.hashCode()
        return result
    }
}


