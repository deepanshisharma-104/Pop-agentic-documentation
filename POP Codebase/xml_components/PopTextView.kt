package com.pop.components.xml_components


import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.pop.compose_components.R
import androidx.core.content.withStyledAttributes


class PopTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val underlineFlag = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    private val noUnderlineFlag = paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return

        context.withStyledAttributes(attrs, R.styleable.PopTextView) {

            // Handle custom font
            val customFont = getString(R.styleable.PopTextView_popCustomFont)
            val fontResId = when (customFont) {
                "1" -> R.font.figtree_regular
                "2" -> R.font.figtree_regular
                "3" -> R.font.figtree_medium
                "4" -> R.font.figtree_semibold
                "5" -> R.font.figtree_bold
                "6" -> R.font.figtree_bold
                else -> R.font.figtree_regular
            }
            typeface = ResourcesCompat.getFont(context, fontResId)

            // Handle underline
            val underlineText = getBoolean(R.styleable.PopTextView_underline, false)
            setUnderline(underlineText)

        }

        // Check if textAppearance is a link style and apply underline automatically
        checkAndApplyLinkUnderline(context, attrs)
    }

    /**
     * Checks if the textAppearance attribute uses a Pop Link style and applies underline.
     * This maintains functionality since android:textDecoration is not available below API 34.
     */
    private fun checkAndApplyLinkUnderline(context: Context, attrs: AttributeSet?) {
        if (attrs == null) return

        try {
            val textAppearanceResId = attrs.getAttributeResourceValue(
                "http://schemas.android.com/apk/res/android",
                "textAppearance",
                0
            )

            if (textAppearanceResId != 0) {
                val resourceName = try {
                    context.resources.getResourceEntryName(textAppearanceResId)
                } catch (e: Exception) {
                    ""
                }
                // Check if it's a link style (TextAppearance.Pop.Link.Large or TextAppearance.Pop.Link.Medium)
                if (resourceName.contains("Link", ignoreCase = true)) {
                    setUnderline(true)
                }
            }
        } catch (e: Exception) {
            // Silently fail if resource lookup fails
        }
    }

    override fun setTextAppearance(resId: Int) {
        super.setTextAppearance(resId)
        // Check if the text appearance is a link style and apply underline
        try {
            val resourceName = context.resources.getResourceEntryName(resId)
            if (resourceName.contains("Link", ignoreCase = true)) {
                setUnderline(true)
            }
        } catch (e: Exception) {
            // Silently fail if resource lookup fails
        }
    }

    /** Public method to toggle underline dynamically */
    fun setUnderline(needUnderline: Boolean) {
        paintFlags = if (needUnderline) underlineFlag else noUnderlineFlag
    }

    /** Utility method to clear compound drawables */
    fun clearAllDrawables() {
        setCompoundDrawables(null, null, null, null)
    }
}
