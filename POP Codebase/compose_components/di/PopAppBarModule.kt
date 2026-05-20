package com.pop.components.compose_components.di

import com.pop.components.ds_components.PopAppBarManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

/**
 * Hilt module for providing PopAppBarManager dependency.
 * 
 * This module provides an activity-scoped singleton instance of PopAppBarManager
 * that can be injected anywhere in the application using Hilt. The manager is
 * scoped to the activity lifecycle, ensuring one instance per activity.
 * 
 * ## Usage in Activity:
 * ```kotlin
 * @AndroidEntryPoint
 * class POPMainActivity : BaseActivity() {
 *     @Inject lateinit var appBarManager: PopAppBarManager
 *     
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         // appBarManager is automatically injected
 *         setupGlobalAppBar()
 *     }
 * }
 * ```
 * 
 * ## Usage in Fragment:
 * ```kotlin
 * @AndroidEntryPoint
 * class MyFragment : Fragment() {
 *     @Inject lateinit var appBarManager: PopAppBarManager
 *     
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         // appBarManager is automatically injected
 *     }
 * }
 * ```
 * 
 * ## Benefits:
 * - ✅ No reflection needed
 * - ✅ Type-safe injection
 * - ✅ Automatic lifecycle management
 * - ✅ Easy to test and mock
 * - ✅ Compile-time verification
 */
@Module
@InstallIn(ActivityComponent::class)
object PopAppBarModule {
    
    /**
     * Provides a singleton instance of PopAppBarManager scoped to the activity.
     * Using @ActivityScoped ensures one instance per activity lifecycle.
     * 
     * This means all fragments within the same activity will share the same
     * PopAppBarManager instance, which is the desired behavior for the app bar.
     * 
     * @return PopAppBarManager instance
     */
    @Provides
    @ActivityScoped
    fun providePopAppBarManager(): PopAppBarManager {
        return PopAppBarManager()
    }
}
