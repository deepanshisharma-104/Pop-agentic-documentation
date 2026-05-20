package com.pop.components.ui.di

import com.pop.components.ui.PopCommonUiManager
import com.pop.components.ui.service.BottomBarService
import com.pop.components.ui.service.BottomBarServiceImpl
import com.pop.components.coachmarks.service.CoachMarkService
import com.pop.components.coachmarks.service.CoachMarkServiceImpl
import com.pop.components.ui.service.LoaderService
import com.pop.components.ui.service.LoaderServiceImpl
import com.pop.components.ui.service.ToastService
import com.pop.components.ui.service.ToastServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * Hilt module for providing PopCommonUiManager and related UI dependencies.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object CommonUiModule {

    /**
     * Provides ToastService implementation.
     */
    @Provides
    @ActivityRetainedScoped
    fun provideToastService(): ToastService {
        return ToastServiceImpl()
    }

    /**
     * Provides LoaderService implementation.
     */
    @Provides
    @ActivityRetainedScoped
    fun provideLoaderService(): LoaderService {
        return LoaderServiceImpl()
    }

    /**
     * Provides BottomBarService implementation.
     */
    @Provides
    @ActivityRetainedScoped
    fun provideBottomBarService(): BottomBarService {
        return BottomBarServiceImpl()
    }

    /**
     * Provides CoachMarkService implementation.
     */
    @Provides
    @ActivityRetainedScoped
    fun provideCoachMarkService(): CoachMarkService {
        return CoachMarkServiceImpl()
    }

    /**
     * Provides PopCommonUiManager for BottomBar, Toast, Loader, and CoachMark functionality.
     */
    @Provides
    @ActivityRetainedScoped
    fun providePopCommonUiManager(
        toastService: ToastService,
        loaderService: LoaderService,
        bottomBarService: BottomBarService,
        coachMarkService: CoachMarkService,
    ): PopCommonUiManager {
        return PopCommonUiManager(
            toastService = toastService,
            loaderService = loaderService,
            bottomBarService = bottomBarService,
            coachMarkService = coachMarkService
        )
    }
}

