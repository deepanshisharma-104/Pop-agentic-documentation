package com.pop.components.animation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * Type converter for AnimationBitmapType enum to store it as String in Room.
 */
class AnimationBitmapTypeConverter {
    @TypeConverter
    fun fromAnimationBitmapType(type: AnimationBitmapType): String {
        return type.name
    }

    @TypeConverter
    fun toAnimationBitmapType(value: String): AnimationBitmapType {
        return AnimationBitmapType.valueOf(value)
    }
}

/**
 * Room database for storing animation bitmaps.
 * 
 * This database is separate from CacheDatabase to maintain separation of concerns.
 */
@Database(
    entities = [AnimationBitmapEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AnimationBitmapTypeConverter::class)
abstract class AnimationDatabase : RoomDatabase() {
    /**
     * Provides access to the animation bitmap DAO.
     */
    abstract fun animationBitmapDao(): AnimationBitmapDao
}

