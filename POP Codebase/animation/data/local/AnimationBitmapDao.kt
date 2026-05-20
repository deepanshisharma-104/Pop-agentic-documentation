package com.pop.components.animation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for animation bitmap operations.
 */
@Dao
interface AnimationBitmapDao {

    /**
     * Inserts or replaces an animation bitmap entity.
     *
     * @param bitmapEntity The entity to insert
     * @return The row ID of the inserted entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBitmap(bitmapEntity: AnimationBitmapEntity): Long

    /**
     * Retrieves an animation bitmap by its animation ID.
     *
     * @param animationId The unique animation ID
     * @return The AnimationBitmapEntity if found, null otherwise
     */
    @Query("SELECT * FROM animation_bitmaps WHERE animationId = :animationId LIMIT 1")
    suspend fun getBitmapByAnimationId(animationId: String): AnimationBitmapEntity?

    /**
     * Retrieves all bitmaps of a specific animation type.
     *
     * @param type The animation type to filter by
     * @return List of AnimationBitmapEntity matching the type
     */
    @Query("SELECT * FROM animation_bitmaps WHERE animationType = :type")
    suspend fun getBitmapsByType(type: AnimationBitmapType): List<AnimationBitmapEntity>

    /**
     * Deletes an animation bitmap by its animation ID.
     *
     * @param animationId The unique animation ID
     * @return Number of rows deleted (should be 0 or 1)
     */
    @Query("DELETE FROM animation_bitmaps WHERE animationId = :animationId")
    suspend fun deleteBitmapByAnimationId(animationId: String): Int

    /**
     * Deletes all animation bitmaps from the table.
     * Used for cleanup on app exit.
     *
     * @return Number of rows deleted
     */
    @Query("DELETE FROM animation_bitmaps")
    suspend fun deleteAllBitmaps(): Int
}
