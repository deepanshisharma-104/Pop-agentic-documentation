package com.pop.components.animation.config

import androidx.annotation.FloatRange
import androidx.annotation.IdRes
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.Guideline

data class MotionLayoutDynamicGuidelineConfig(
    val motionLayout: MotionLayout,
    val guideline: Guideline,
    @IdRes
    val startConstraintSetId: Int,
    @IdRes
    val endConstraintSetId: Int,
    @FloatRange(from = 0.0, to = 1.0)
    val splitAnchorPercent: Float,
)
