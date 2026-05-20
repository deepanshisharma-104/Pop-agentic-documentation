package com.pop.components.animation.di

import android.content.Context
import androidx.room.Room
import com.pop.components.animation.data.local.AnimationBitmapDao
import com.pop.components.animation.data.local.AnimationDatabase
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing AnimationDatabase and AnimationBitmapDao dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AnimationDatabaseModule {

    /**
     * Provides a singleton instance of AnimationDatabase.
     *
     * @param context Application context
     * @return AnimationDatabase instance
     */
    @Provides
    @Singleton
    fun provideAnimationDatabase(@ApplicationContext context: Context): AnimationDatabase {
        return Room.databaseBuilder(
            context,
            AnimationDatabase::class.java,
            "animation_database"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    /**
     * Provides AnimationBitmapDao instance.
     *
     * @param database AnimationDatabase instance
     * @return AnimationBitmapDao instance
     */
    @Provides
    @Singleton
    fun provideAnimationBitmapDao(database: Lazy<AnimationDatabase>): AnimationBitmapDao {
        return database.get().animationBitmapDao()
    }
}

