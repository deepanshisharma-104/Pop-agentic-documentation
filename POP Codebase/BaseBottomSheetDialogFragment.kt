package com.pop.components

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColorInt
import androidx.core.view.isNotEmpty
import androidx.fragment.app.DialogFragment
import com.pop.components.animation.utils.resetScreenScale
import com.pop.components.animation.utils.scaleDownScreen
import com.pop.compose_components.R

abstract class BaseBottomSheetDialogFragment : DialogFragment() {

    abstract fun onScaleProgress(scale: Float)

    /**
     * Whether to use gradient background (SurfacePrimaryLarge) or transparent background.
     * Matches RoundedSquareBottomSheet's useGradientBackground parameter.
     * Set this before the view is created (e.g., in onCreate or via arguments).
     */
    abstract fun shouldUseGradientBackgroundForContent(): Boolean

    private var contentWrapper: FrameLayout? = null

    override fun getTheme() = R.style.BottomDialogGradientScrim

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomDialogGradientScrim)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window.setGravity(Gravity.BOTTOM)

            // Make the window fully transparent so we can control the scrim
            window.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }

        // Add gradient scrim overlay and wrap content at bottom
        setupDialogLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Apply blur effect to the activity's root content view
        applyBlurEffect(true)

        dialog?.scaleDownScreen { currentScale ->
            if (currentScale <= 1f) {
                onScaleProgress(currentScale)
            }
        }
    }

    /**
     * Sets up the dialog layout with gradient scrim and positions content at the bottom.
     */
    private fun setupDialogLayout() {
        val decorView = dialog?.window?.decorView as? ViewGroup ?: return
        val contentParent = decorView.findViewById<ViewGroup>(android.R.id.content) ?: return

        // Get the fragment's content view
        val fragmentContent = if (contentParent.isNotEmpty()) contentParent.getChildAt(0) else return

        // Remove the content from its parent
        contentParent.removeView(fragmentContent)

        // Create gradient drawable for scrim
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                "#000D0D0D".toColorInt(), // 0% opacity at top
                "#B30D0D0D".toColorInt(), // 70% opacity
                "#B30D0D0D".toColorInt()  // 70% opacity at bottom
            )
        )

        // Create a wrapper FrameLayout that fills the screen
        contentWrapper = FrameLayout(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            background = gradientDrawable
            isClickable = true
            isFocusable = true
            setOnClickListener {
                // Dismiss when clicking on scrim area (if cancellable)
                if (isCancelable) {
                    dismiss()
                }
            }
        }

        // Add the fragment content to the wrapper, positioned at the bottom
        // Preserve margins from the original layout params if they exist
        val originalParams = fragmentContent.layoutParams as? ViewGroup.MarginLayoutParams
        val contentLayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.BOTTOM
            // Copy margins from original layout params
            originalParams?.let {
                leftMargin = it.leftMargin
                topMargin = it.topMargin
                rightMargin = it.rightMargin
                bottomMargin = it.bottomMargin
                marginStart = it.marginStart
                marginEnd = it.marginEnd
            }
        }
        fragmentContent.layoutParams = contentLayoutParams

        // Prevent clicks on content from dismissing
        fragmentContent.isClickable = true

        contentWrapper?.addView(fragmentContent)

        // Add the wrapper to the content parent
        contentParent.addView(contentWrapper)
    }

    override fun onDismiss(dialogInterface: DialogInterface) {
        // Remove blur effect before dismissing
        applyBlurEffect(false)

        // Clean up wrapper
        contentWrapper = null

        dialog?.resetScreenScale(
            onScaleProgress = { currentScale ->
                onScaleProgress(currentScale)
            },
            onScaleComplete = {
                super.onDismiss(dialogInterface)
            }
        )
    }

    /**
     * Applies or removes blur effect on the parent activity's root content view.
     * Blur is only available on API 31+ (Android 12+).
     */
    private fun applyBlurEffect(apply: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val activityRootView = activity?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0)
            activityRootView?.setRenderEffect(
                if (apply) {
                    RenderEffect.createBlurEffect(
                        10f,
                        10f,
                        Shader.TileMode.CLAMP
                    )
                } else {
                    null
                }
            )
        }
    }

    /**
     * Applies the appropriate background to the content container based on [useGradientBackgroundForContent].
     * Call this method in your subclass's onViewCreated or setupViews with your content container view.
     *
     * @param contentContainer The view to apply the background to (typically the rounded rectangle container)
     */
    protected fun applyContentBackground(contentContainer: View) {
        val backgroundRes = if (shouldUseGradientBackgroundForContent()) {
            R.drawable.bg_rounded_square_bottom_sheet
        } else {
            R.drawable.bg_rounded_square_transparent
        }
        contentContainer.background = ContextCompat.getDrawable(requireContext(), backgroundRes)
    }

    /**
     * method to show and hide progress depending on boolean passed
     * @param toShowProgress set true to show progress, false otherwise
     */
    fun toShowHideProgress(toShowProgress: Boolean, progressLoader: ProgressBar?) {
        if (toShowProgress) {
            showProgress(progressLoader)
        } else {
            hideProgress(progressLoader)
        }
    }

    /**
     * method to show progress loader
     */
    private fun showProgress(progressLoader: ProgressBar?) {
        progressLoader?.visibility = View.VISIBLE
    }

    /**
     * method to hide progress loader
     */
    private fun hideProgress(progressLoader: ProgressBar?) {
        progressLoader?.visibility = View.GONE
    }

    protected fun EditText.watchText(afterChanged: (text: String?) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable) {
                afterChanged(editable.toString())
            }
        })
    }

    fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    open fun showToast(msg: String?) {
        try {
            if (msg != null && msg.trim { it <= ' ' }.isNotEmpty()) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("", "" + e)
        }
    }
}