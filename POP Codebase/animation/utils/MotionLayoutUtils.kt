package com.pop.components.animation.utils

import androidx.constraintlayout.widget.ConstraintSet
import com.pop.components.animation.config.MotionLayoutDynamicGuidelineConfig
import timber.log.Timber

object MotionLayoutUtils {

    /**
     * Sets the center guideline percent dynamically at runtime.
     * Views aligned with the guideline (iv_upper and iv_lower) will automatically
     * reset their constraints once the update is made.
     * Also updates the MotionLayout constraint sets (start and end) so animations
     * properly reflect the new guideline position.
     *
     * This method uses reflection to update ConstraintSet guidelines, trying multiple
     * approaches to ensure compatibility across different Android/ConstraintLayout versions.
     *
     * @param config Configuration containing MotionLayout, guideline, and split anchor percent
     */
    fun setCenterGuidelinePercent(config: MotionLayoutDynamicGuidelineConfig) {
        val (motionLayout, centerGuideline, startConstraintSetId, endConstraintSetId, splitAnchorPercent) = config

        // Validate split anchor percent
        if (splitAnchorPercent < 0f || splitAnchorPercent > 1f) {
            Timber.tag("MotionLayoutUtils").w("Guideline percent must be between 0.0 and 1.0, got: $splitAnchorPercent")
            return
        }

        // Update the guideline in the layout
        centerGuideline.setGuidelinePercent(splitAnchorPercent)

        // Update the guideline in MotionLayout constraint sets
        try {
            // Get the start and end constraint sets
            val startConstraintSet = motionLayout.getConstraintSet(startConstraintSetId)
            val endConstraintSet = motionLayout.getConstraintSet(endConstraintSetId)

            if (startConstraintSet != null && endConstraintSet != null) {
                // Clone the constraint sets to get mutable copies
                val clonedStartSet = ConstraintSet()
                clonedStartSet.clone(startConstraintSet)

                val clonedEndSet = ConstraintSet()
                clonedEndSet.clone(endConstraintSet)

                // Update the guideline percent in both constraint sets
                // Try multiple approaches to set the guideline percent for compatibility
                var guidelineUpdated = false

                // Approach 1: Try setGuidelinePercent method (preferred, if available)
                try {
                    val setGuidelineMethod = ConstraintSet::class.java.getMethod(
                        "setGuidelinePercent", Int::class.javaPrimitiveType, Float::class.javaPrimitiveType
                    )
                    setGuidelineMethod.invoke(clonedStartSet, centerGuideline.id, splitAnchorPercent)
                    setGuidelineMethod.invoke(clonedEndSet, centerGuideline.id, splitAnchorPercent)
                    guidelineUpdated = true
                } catch (e: NoSuchMethodException) {
                    // Method doesn't exist in this ConstraintLayout version, try next approach
                }

                // Approach 2: Try createHorizontalGuideline method (fallback)
                if (!guidelineUpdated) {
                    try {
                        val createGuidelineMethod = ConstraintSet::class.java.getMethod(
                            "createHorizontalGuideline",
                            Int::class.javaPrimitiveType,
                            Float::class.javaPrimitiveType,
                            Int::class.javaPrimitiveType
                        )
                        // Create guideline at the specified percent (0 = percent-based)
                        createGuidelineMethod.invoke(clonedStartSet, centerGuideline.id, splitAnchorPercent, 0)
                        createGuidelineMethod.invoke(clonedEndSet, centerGuideline.id, splitAnchorPercent, 0)
                        guidelineUpdated = true
                    } catch (e: NoSuchMethodException) {
                        // Method doesn't exist, try next approach
                    }
                }

                // Approach 3: Use reflection to set guidePercent field directly (last resort)
                if (!guidelineUpdated) {
                    try {
                        val startParams = clonedStartSet.getParameters(centerGuideline.id)
                        val endParams = clonedEndSet.getParameters(centerGuideline.id)

                        if (startParams != null) {
                            val guidePercentField = startParams.javaClass.getDeclaredField("guidePercent")
                            guidePercentField.isAccessible = true
                            guidePercentField.setFloat(startParams, splitAnchorPercent)
                        }
                        if (endParams != null) {
                            val guidePercentField = endParams.javaClass.getDeclaredField("guidePercent")
                            guidePercentField.isAccessible = true
                            guidePercentField.setFloat(endParams, splitAnchorPercent)
                        }
                        guidelineUpdated = true
                    } catch (e: NoSuchFieldException) {
                        Timber.tag("MotionLayoutUtils").w(e, "Could not set guideline percent via reflection: field not found")
                    } catch (e: IllegalAccessException) {
                        Timber.tag("MotionLayoutUtils").w(e, "Could not set guideline percent via reflection: access denied")
                    } catch (e: Exception) {
                        Timber.tag("MotionLayoutUtils").w(e, "Could not set guideline percent via reflection: ${e.message}")
                    }
                }

                if (!guidelineUpdated) {
                    Timber.tag("MotionLayoutUtils").w(
                        "Could not update guideline in constraint sets using any reflection approach. " +
                                "MotionLayout will use guideline from layout directly."
                    )
                }

                // Update the constraint sets in MotionLayout
                motionLayout.updateState(startConstraintSetId, clonedStartSet)
                motionLayout.updateState(endConstraintSetId, clonedEndSet)
            }

            // Invalidate the MotionLayout to ensure it picks up the changes
            motionLayout.invalidate()

            Timber.tag("MotionLayoutUtils").d("Center guideline percent set to: $splitAnchorPercent in layout and constraint sets")
        } catch (e: Exception) {
            Timber.tag("MotionLayoutUtils").e(e, "Error updating constraint sets: ${e.message}")
            // Note: If FirebaseCrashlytics is available in the app module, uncomment the following:
            // try {
            //     com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance().recordException(e)
            // } catch (ignored: Exception) {
            //     // Crashlytics not available
            // }
        }

        // Request layout to ensure views constrained to the guideline update properly
        motionLayout.requestLayout()
    }
}