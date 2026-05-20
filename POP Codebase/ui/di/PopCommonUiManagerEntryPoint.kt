package com.pop.components.ui.di

import com.pop.components.ui.PopCommonUiManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * EntryPoint for accessing PopCommonUiManager from non-injected contexts
 * (e.g., Composables, utility classes, etc.)
 * 
 * Installed in ActivityComponent so it can be accessed via EntryPointAccessors.fromActivity().
 * PopCommonUiManager itself is provided in ActivityRetainedComponent, which ActivityComponent
 * can access as its parent component.
 */
@EntryPoint
@InstallIn(ActivityComponent::class)
interface PopCommonUiManagerEntryPoint {
    fun popCommonUiManager(): PopCommonUiManager
}

