package com.pop.components.xml_components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.widget.ImageViewCompat
import com.pop.components.theme.IconName
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopIconResources
import com.pop.components.theme.toDp
import com.pop.compose_components.R

/**
 * Custom ImageView for displaying design system icons in XML layouts.
 * 
 * This view automatically resolves icon resources based on icon name and style,
 * and applies the correct size and tint.
 * 
 * Usage in XML:
 * ```xml
 * <com.pop.components.xml_components.PopImageView
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     app:popIconName="ic_arrow_down"
 *     app:popIconStyle="outline"
 *     app:popIconSize="medium"
 *     app:popIconTint="@color/white" />
 * ```
 */
class PopImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var iconName: String? = null
    private var iconStyle: IconStyle = IconStyle.Outline
    private var iconSize: IconSize = IconSize.Medium
    private var cachedSizePx: Int = 0

    init {
        scaleType = ImageView.ScaleType.FIT_CENTER
        adjustViewBounds = true
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return

        context.withStyledAttributes(attrs, R.styleable.PopImageView) {
            // Get icon name
            iconName = getString(R.styleable.PopImageView_popIconName)

            // Get icon style
            val styleValue = getInt(R.styleable.PopImageView_popIconStyle, 0)
            iconStyle = when (styleValue) {
                0 -> IconStyle.Outline
                1 -> IconStyle.Filled
                else -> IconStyle.Outline
            }

            // Get icon size
            val sizeValue = getInt(R.styleable.PopImageView_popIconSize, 1)
            iconSize = when (sizeValue) {
                0 -> IconSize.Small
                1 -> IconSize.Medium
                2 -> IconSize.Large
                3 -> IconSize.XLarge
                else -> IconSize.Medium
            }

            // Get tint color
            val tintColor = getColorStateList(R.styleable.PopImageView_popIconTint)
            if (tintColor != null) {
                ImageViewCompat.setImageTintList(this@PopImageView, tintColor)
            }

            // Apply icon
            applyIcon()
        }
    }

    /**
     * Set the icon by type-safe icon name and style.
     * 
     * @param iconName The type-safe icon name from the Icons namespace (e.g., Icons.ArrowDown)
     * @param style The icon style - Outline or Filled (defaults to Outline)
     * 
     * @example
     * ```
     * iconView.setIcon(Icons.Heart, IconStyle.Filled)
     * ```
     */
    fun setIcon(iconName: IconName, style: IconStyle = IconStyle.Outline) {
        this.iconName = iconName.value
        this.iconStyle = style
        applyIcon()
    }

    /**
     * Set the icon style
     */
    fun setIconStyle(style: IconStyle) {
        this.iconStyle = style
        applyIcon()
    }

    /**
     * Set the icon size
     */
    fun setIconSize(size: IconSize) {
        this.iconSize = size
        updateSize()
    }

    /**
     * Set the icon tint color
     */
    fun setIconTint(colorRes: Int) {
        val tintColor = ContextCompat.getColorStateList(context, colorRes)
        ImageViewCompat.setImageTintList(this, tintColor)
    }

    /**
     * Set the icon tint color from color value
     */
    fun setIconTintColor(color: Int) {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
    }

    private fun applyIcon() {
        iconName?.let { name ->
            // Use internal method for XML attribute parsing
            val resourceId = PopIconResources.getIconResourceIdFromString(name, iconStyle)
            resourceId?.let { id ->
                setImageResource(id)
                updateSize()
            }
        }
    }

    private fun updateSize() {
        cachedSizePx = calculateSizePx()
        
        // Update layout params if they exist
        layoutParams?.let {
            it.width = cachedSizePx
            it.height = cachedSizePx
            requestLayout()
        }
        
        // Also set minimum size as fallback
        minimumWidth = cachedSizePx
        minimumHeight = cachedSizePx
    }
    
    private fun calculateSizePx(): Int {
        val sizeDp = iconSize.toDp()
        return (sizeDp.value * resources.displayMetrics.density).toInt()
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Recalculate if size changed (e.g., density change)
        val currentSizePx = calculateSizePx()
        if (currentSizePx != cachedSizePx) {
            cachedSizePx = currentSizePx
        }
        
        val widthSpec = if (layoutParams?.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            MeasureSpec.makeMeasureSpec(cachedSizePx, MeasureSpec.EXACTLY)
        } else {
            widthMeasureSpec
        }
        
        val heightSpec = if (layoutParams?.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            MeasureSpec.makeMeasureSpec(cachedSizePx, MeasureSpec.EXACTLY)
        } else {
            heightMeasureSpec
        }
        
        super.onMeasure(widthSpec, heightSpec)
    }
}