package com.pop.components.animation.di

import com.pop.components.animation.AnimationManager
import com.pop.components.animation.config.AnimationConfigStackManager
import com.pop.components.animation.data.local.AnimationBitmapDao
import com.pop.components.animation.repository.AnimationBitmapRepository
import com.pop.components.animation.repository.AnimationBitmapRepositoryImpl
import com.pop.components.animation.serialization.AnimationConfigSerializer
import com.pop.components.animation.service.AnimationEventBus
import com.pop.components.animation.service.AnimationEventBusImpl
import com.pop.components.animation.strategy.AnimationStrategyFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Hilt module for providing AnimationManager and related dependencies.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object AnimationModule {

    /**
     * Provides AnimationBitmapRepository implementation.
     * 
     * Returns AnimationBitmapRepositoryImpl which handles bitmap storage and retrieval
     * for animation types (Split Screen, Mask Reveal) using Room database.
     * 
     * @param animationBitmapDao Lazy DAO instance for accessing animation bitmaps
     * @return AnimationBitmapRepository implementation for caching animation bitmaps
     */
    @Provides
    @ActivityRetainedScoped
    internal fun provideAnimationBitmapRepository(
        animationBitmapDao: Lazy<AnimationBitmapDao>
    ): AnimationBitmapRepository {
        return AnimationBitmapRepositoryImpl(animationBitmapDao)
    }

    /**
     * Provides AnimationConfigStackManager.
     */
    @Provides
    @ActivityRetainedScoped
    internal fun provideAnimationConfigStackManager(): AnimationConfigStackManager {
        return AnimationConfigStackManager()
    }

    /**
     * Provides AnimationConfigSerializer with automatic class discovery.
     */
    @Provides
    @ActivityRetainedScoped
    internal fun provideAnimationConfigSerializer(): AnimationConfigSerializer {
        return AnimationConfigSerializer()
    }

    /**
     * Provides AnimationStrategyFactory.
     */
    @Provides
    @ActivityRetainedScoped
    internal fun provideAnimationStrategyFactory(): AnimationStrategyFactory {
        return AnimationStrategyFactory()
    }

    /**
     * Provides AnimationEventBus implementation.
     */
    @Provides
    @ActivityRetainedScoped
    internal fun provideAnimationEventBus(): AnimationEventBus {
        return AnimationEventBusImpl()
    }

    /**
     * Provides AnimationManager.
     */
    @Provides
    @ActivityRetainedScoped
    internal fun provideAnimationManager(
        animationStrategyFactory: AnimationStrategyFactory,
        bitmapRepository: AnimationBitmapRepository,
        configStackManager: AnimationConfigStackManager,
        eventBus: AnimationEventBus,
        serializer: AnimationConfigSerializer
    ): AnimationManager {
        return AnimationManager(
            animationStrategyFactory = animationStrategyFactory,
            bitmapRepository = bitmapRepository,
            configStackManager = configStackManager,
            eventBus = eventBus,
            serializer = serializer
        )
    }

}
